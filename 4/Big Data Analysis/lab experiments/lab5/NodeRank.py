import sys

def solve():
    line1 = sys.stdin.readline().strip().split()
    if not line1: return
    n = int(line1[0])
    beta = float(line1[1])

    incoming_links = [[] for _ in range(n)]
    out_degrees = [0] * n

    for i in range(n):
        neighbors = list(map(int, sys.stdin.readline().strip().split()))
        out_degrees[i] = len(neighbors)
        for neighbor in neighbors:
            incoming_links[neighbor].append(i)

    num_queries = int(sys.stdin.readline().strip())
    queries = []
    max_iteration = 0
    for _ in range(num_queries):
        node_idx, iteration = map(int, sys.stdin.readline().strip().split())
        queries.append((node_idx, iteration))
        if iteration > max_iteration:
            max_iteration = iteration

    stored_ranks = [[0.0] * n for _ in range(max_iteration + 1)]
    initial_rank = 1.0 / n
    for i in range(n):
        stored_ranks[0][i] = initial_rank

    teleport_prob = (1.0 - beta) / n
    for t in range(1, max_iteration + 1):
        for i in range(n):
            link_sum = 0.0
            for predecessor in incoming_links[i]:
                link_sum += stored_ranks[t-1][predecessor] / out_degrees[predecessor]
            stored_ranks[t][i] = teleport_prob + beta * link_sum

    for node_idx, iteration in queries:
        result = stored_ranks[iteration][node_idx]
        print(f"{result:.10f}")

if __name__ == "__main__":
    solve()
