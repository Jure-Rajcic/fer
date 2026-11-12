"""
Linear Regression using L1 norm (Absolute Error Minimization)
Using Linear Programming with PuLP

For a given set of points, finds the line y = ax + b that minimizes
the sum of absolute errors (distances from points to the line).
"""

import numpy as np
import matplotlib.pyplot as plt
from pulp import LpProblem, LpMinimize, LpVariable, lpSum, LpStatus, value

def generate_points(seed, n_points=100):
    """
    Generate random points for the regression problem.
    
    Args:
        seed: Random seed (use last two digits of student ID)
        n_points: Number of points to generate (default: 100)
    
    Returns:
        x, y: Arrays of x and y coordinates
    """
    np.random.seed(seed)
    
    # Generate x values uniformly distributed
    x = np.random.uniform(0, 10, n_points)
    
    # Generate y values with some linear relationship plus noise
    # True relationship: y ≈ 2x + 1 with noise
    y = 2 * x + 1 + np.random.normal(0, 2, n_points)
    
    return x, y

def solve_l1_regression(x, y):
    """
    Solve L1 regression using linear programming.
    
    Minimizes: sum of |y_i - (a*x_i + b)|
    
    Args:
        x: Array of x coordinates
        y: Array of y coordinates
    
    Returns:
        a, b: Coefficients of the line y = ax + b
    """
    n = len(x)
    
    # Create the LP problem
    prob = LpProblem("L1_Regression", LpMinimize)
    
    # Decision variables
    a = LpVariable("a", lowBound=None, upBound=None)  # slope
    b = LpVariable("b", lowBound=None, upBound=None)  # intercept
    
    # Auxiliary variables for absolute values
    # For each point i: |y_i - (a*x_i + b)| = t_i
    t = [LpVariable(f"t_{i}", lowBound=0) for i in range(n)]
    
    # Objective: minimize sum of absolute errors
    prob += lpSum(t)
    
    # Constraints: t_i >= |y_i - (a*x_i + b)|
    # This is equivalent to:
    #   t_i >= y_i - (a*x_i + b)
    #   t_i >= -(y_i - (a*x_i + b)) = (a*x_i + b) - y_i
    for i in range(n):
        prob += t[i] >= y[i] - (a * x[i] + b), f"abs_pos_{i}"
        prob += t[i] >= (a * x[i] + b) - y[i], f"abs_neg_{i}"
    
    # Solve the problem
    prob.solve()
    
    # Extract solution
    a_opt = value(a)
    b_opt = value(b)
    
    return a_opt, b_opt, prob

def plot_results(x, y, a, b, seed):
    """
    Plot the points and the regression line.
    
    Args:
        x, y: Point coordinates
        a, b: Line coefficients
        seed: Random seed used
    """
    plt.figure(figsize=(10, 6))
    
    # Plot points
    plt.scatter(x, y, alpha=0.6, s=50, label='Točke', color='blue')
    
    # Plot regression line
    x_line = np.array([min(x), max(x)])
    y_line = a * x_line + b
    plt.plot(x_line, y_line, 'r-', linewidth=2, 
             label=f'Pravac: y = {a:.3f}x + {b:.3f}')
    
    plt.xlabel('x', fontsize=12)
    plt.ylabel('y', fontsize=12)
    plt.title(f'L1 Linearna Regresija (seed = {seed})', fontsize=14)
    plt.legend(fontsize=11)
    plt.grid(True, alpha=0.3)
    
    plt.tight_layout()
    plt.savefig(f'regression_seed_{seed}.png', dpi=300, bbox_inches='tight')
    plt.show()

def calculate_errors(x, y, a, b):
    """
    Calculate error statistics.
    
    Args:
        x, y: Point coordinates
        a, b: Line coefficients
    
    Returns:
        Dictionary with error statistics
    """
    y_pred = a * x + b
    errors = np.abs(y - y_pred)
    
    return {
        'total_absolute_error': np.sum(errors),
        'mean_absolute_error': np.mean(errors),
        'max_absolute_error': np.max(errors),
        'median_absolute_error': np.median(errors)
    }

def main():
    """
    Main function to run the L1 regression analysis.
    """
    # PROMIJENI OVO: Postavi seed na zadnje dvije znamenke svog matičnog broja
    SEED = 53  # Primjer: ako je matični broj ...42, seed je 42
    
    print("="*60)
    print("L1 LINEARNA REGRESIJA - MINIMIZACIJA APSOLUTNE POGREŠKE")
    print("="*60)
    print(f"\nSeed: {SEED}")
    
    # 1. Generate points
    print(f"\nGeneriranje {100} točaka...")
    x, y = generate_points(SEED, n_points=100)
    print(f"Točke generirane: {len(x)} točaka")
    print(f"  x raspon: [{np.min(x):.2f}, {np.max(x):.2f}]")
    print(f"  y raspon: [{np.min(y):.2f}, {np.max(y):.2f}]")
    
    # 2. Solve L1 regression
    print("\nRješavanje L1 regresije pomoću linearnog programiranja...")
    a, b, prob = solve_l1_regression(x, y)
    
    # 3. Display results
    print("\n" + "="*60)
    print("REZULTATI")
    print("="*60)
    print(f"\nStatus: {LpStatus[prob.status]}")
    print(f"\nJednadžba pravca:")
    print(f"  y = {a:.6f}x + {b:.6f}")
    print(f"\nKoeficijenti:")
    print(f"  a (nagib) = {a:.6f}")
    print(f"  b (odsječak) = {b:.6f}")
    
    # 4. Calculate and display errors
    errors = calculate_errors(x, y, a, b)
    print(f"\nStatistika pogreške:")
    print(f"  Ukupna apsolutna pogreška: {errors['total_absolute_error']:.6f}")
    print(f"  Srednja apsolutna pogreška: {errors['mean_absolute_error']:.6f}")
    print(f"  Maksimalna apsolutna pogreška: {errors['max_absolute_error']:.6f}")
    print(f"  Medijan apsolutne pogreške: {errors['median_absolute_error']:.6f}")
    
    # 5. Plot results
    print(f"\nIzrada grafičkog prikaza...")
    plot_results(x, y, a, b, SEED)
    print(f"Grafikon spremljen kao: regression_seed_{SEED}.png")
    
    print("\n" + "="*60)
    print("ZAVRŠENO")
    print("="*60)

if __name__ == "__main__":
    main()
