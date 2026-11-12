from mpi4py import MPI
import sys
import time

BOARD_WIDTH = 7
MAX_DEPTH = 7


def check_winner(board, last_col):
    last_row = len(board[last_col]) - 1
    check_lists = [board[last_col]]
    row_list, diag_1, diag_2 = {}, {}, {}
    var_x, var_y = last_row - last_col, last_row + last_col

    for col_index, col in enumerate(board):
        if last_row in col:
            row_list[col_index] = col[last_row]

    for col in range(BOARD_WIDTH):
        for row in range(last_row - 7, last_row + 7):
            if row in board[col]:
                if row - col == var_x:
                    diag_1[col] = board[col][row]
                if row + col == var_y:
                    diag_2[col] = board[col][row]

    check_lists.extend([diag_1, diag_2, row_list])
    for line in check_lists:
        if len(line) >= 4:
            if win_condition(line) != "0":
                return win_condition(line)
    return "0"


def win_condition(line):
    count = 1
    for index in range(BOARD_WIDTH):
        if index > 0 and (index - 1) in line and index in line:
            if line[index - 1] == line[index]:
                count += 1
                if count == 4:
                    return line[index]
            else:
                count = 1
        else:
            count = 1
    return "0"


def evaluate(board, last_col, depth, max_depth):
    winner = check_winner(board, last_col)
    if winner == "C":
        return 1
    elif winner == "P":
        return -1
    if depth == max_depth - 1:
        return 0

    depth += 1
    current = "C" if depth % 2 == 0 else "P"
    total = 0
    valid_moves = 0
    all_lost, all_won = True, True

    for col in range(BOARD_WIDTH):
        if len(board[col]) >= 100:  # arbitrary limit for "infinite height"
            continue
        board[col][len(board[col])] = current
        result = evaluate(board, col, depth, max_depth)
        board[col].pop(len(board[col]) - 1, None)

        valid_moves += 1
        if result > -1:
            all_lost = False
        if result != 1:
            all_won = False

        if result == 1 and current == "C":
            return 1
        if result == -1 and current == "P":
            return -1

        total += result

    if valid_moves == 0:
        return 0
    if all_won:
        return 1
    if all_lost:
        return -1

    return total / valid_moves


def get_best_move(board):
    values = [None] * BOARD_WIDTH
    sent = []
    dest_rank = 1

    for col in range(BOARD_WIDTH):
        if len(board[col]) >= 100:
            values[col] = float("-inf")
            continue
        if size > 1 and dest_rank < size:
            comm.send({"board": board, "column": col, "depth": 0}, dest=dest_rank, tag=0)
            sent.append(col)
            dest_rank += 1
        else:
            board[col][len(board[col])] = "C"
            values[col] = evaluate(board, col, 0, MAX_DEPTH)
            board[col].pop(len(board[col]) - 1, None)

    for _ in sent:
        data = comm.recv(source=MPI.ANY_SOURCE, tag=0)
        values[data["column"]] = data["value"]

    print("Move values:", [f"{v:.3f}" if v is not None else "X" for v in values])
    best = max((v for v in values if v is not None), default=None)
    return values.index(best) if best is not None else 0


class Game:
    def __init__(self, width):
        self.board = [{} for _ in range(width)]

    def enter(self, col, player):
        self.board[col][len(self.board[col])] = player

    def __repr__(self):
        result = ""
        max_row = max(len(col) for col in self.board)
        for row in range(max_row - 1, -1, -1):
            for col in self.board:
                result += col.get(row, "=")
            result += "\n"
        return result


comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()

if size < 1 or size > 8:
    if rank == 0:
        print("MPI size must be in range 1 to 8.")
    sys.exit(1)

if rank == 0:
    game = Game(BOARD_WIDTH)
    while True:
        try:
            col_num = int(input("Player move (0-6): "))
            if 0 <= col_num < BOARD_WIDTH:
                game.enter(col_num, "P")
                if check_winner(game.board, col_num) == "P":
                    print(game)
                    print("Player wins!")
                    break
                print(game)

                start = time.time()
                move = get_best_move(game.board)
                end = time.time()

                print(f"Computer chose: {move} (Calculation time: {end - start:.2f}s)")
                game.enter(move, "C")
                print(game)
                if check_winner(game.board, move) == "C":
                    print("Computer wins!")
                    break
        except ValueError:
            print("Invalid input. Enter a number between 0 and 6.")
else:
    while True:
        status = MPI.Status()
        data = comm.recv(source=0, tag=0, status=status)
        board = data["board"]
        col = data["column"]
        depth = data["depth"]
        board[col][len(board[col])] = "C"
        val = evaluate(board, col, depth, MAX_DEPTH)
        board[col].pop(len(board[col]) - 1, None)
        comm.send({"value": val, "column": col}, dest=0, tag=0)
