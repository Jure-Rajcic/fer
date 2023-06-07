import re
from typing import Dict, List

class Cell:
    def __init__(self, sheet, address: str, content: str = ""):
        self.sheet = sheet
        self.address = address
        self.exp = content
        self.value = None
        self.dependencies = []

    def update(self, content: str):
        self.exp = content
        self.evaluate()

    def evaluate(self):
        refs = self.sheet.getrefs(self)
        self.dependencies = refs
        variables = {ref.address: ref.value for ref in refs}
        self.value = self.sheet.evaluate(self.exp, variables)
        self.sheet.propagate_changes(self)

class Sheet:
    def __init__(self, rows: int, columns: int):
        self.cells = {}
        self.rows = rows
        self.columns = columns
        for r in range(rows):
            for c in range(columns):
                address = chr(ord('A') + c) + str(r + 1)
                self.cells[address] = Cell(self, address)

    def cell(self, ref: str) -> Cell:
        return self.cells[ref]

    def set(self, ref: str, content: str):
        cell = self.cell(ref)
        cell.update(content)

    def getrefs(self, cell: Cell) -> List[Cell]:
        refs = re.findall(r'[A-Z][1-9][0-9]*', cell.exp)
        return [self.cell(ref) for ref in refs]

    def evaluate(self, exp: str, variables: Dict[str, int]) -> int:
        if not re.search(r'[A-Za-z]', exp):
            return eval(exp) if exp else 0

        for var, value in variables.items():
            print(f"Replacing {var} with {value}")
            exp = exp.replace(var, str(value))
        return eval(exp)

    def propagate_changes(self, updated_cell: Cell):
        for cell in self.cells.values():
            if updated_cell in cell.dependencies:
                cell.evaluate()

    def print(self):
        column_names = [chr(ord('A') + i) for i in range(self.columns)]
        max_cell_size = max(len(str(self.cells[c + str(r + 1)].value or 0)) for c in column_names for r in range(self.rows))

        header = ' ' * (max(3, len(str(self.rows))) + 1) + '|'
        for col_name in column_names:
            header += f"{col_name:^{max_cell_size}}|"
        print(header)

        for r in range(self.rows):
            row = f"{r + 1:>{max(3, len(str(self.rows)))}} |"
            for c in column_names:
                address = c + str(r + 1)
                value = self.cells[address].value or 0
                row += f"{str(value):^{max_cell_size}}|"
            print(row)





if __name__ == "__main__":
    s = Sheet(5, 5)
    print()

    s.set('A1', '2')
    s.set('A2', '5')
    s.set('A3', 'A1+A2')
    s.print()
    print()

    s.set('A1', '4')
    s.set('A4', 'A1+A3')
    s.print()
    print()

    try:
        s.set('A1', 'A3')
    except RuntimeError as e:
        print("Caught exception:", e)
    s.print()
    print()
