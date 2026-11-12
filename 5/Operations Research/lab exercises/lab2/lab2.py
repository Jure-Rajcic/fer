import numpy as np
import pulp

# JMBAG: 0036536053 -> 36536053 + 21 = 3653605321
SEED = 3653605321
rng = np.random.default_rng(SEED)

# Generiranje matrica troškova
mali_costs = rng.integers(low=1, high=21, size=(8, 8))
veliki_costs = rng.integers(low=1, high=21, size=(16, 16))

# Konstrukcija assignment problema
def construct_problem(costs):
    """
    Za zadanu numpy matricu costs konstruira PuLP model
    problema pridruživanja (assignment problem).
    """
    n, m = costs.shape

    model = pulp.LpProblem("Assignment_Problem", pulp.LpMinimize)

    # Binarne varijable x_{i,j}
    x = pulp.LpVariable.dicts(
        "x",
        ((i, j) for i in range(n) for j in range(m)),
        lowBound=0,
        upBound=1,
        cat=pulp.LpBinary
    )

    # Funkcija cilja
    model += pulp.lpSum(costs[i, j] * x[(i, j)] for i in range(n) for j in range(m))

    # Svaki radnik dobiva točno jedan zadatak
    for i in range(n):
        model += pulp.lpSum(x[(i, j)] for j in range(m)) == 1

    # Svaki zadatak ide točno jednom radniku
    for j in range(m):
        model += pulp.lpSum(x[(i, j)] for i in range(n)) == 1

    return model

# LP/MILP pristup
def solve_LP(model):
    solver = pulp.PULP_CBC_CMD(msg=False)
    model.solve(solver)
    return model

# CP pristup
def solve_CP(model):
    try:
        solver = pulp.PULP_CHOCO_CMD(msg=False)
        model.solve(solver)
    except pulp.PulpSolverError:
        # Choco nije instaliran u testnom okruženju
        solver = pulp.PULP_CBC_CMD(msg=False)
        model.solve(solver)

    return model
