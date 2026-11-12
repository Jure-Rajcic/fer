#!/usr/bin/env python3
"""
Test script to verify all dependencies are installed correctly
"""

import sys

def test_imports():
    """Test if all required packages can be imported"""
    print("Testing package imports...")
    
    try:
        import pulp
        print("✓ PuLP installed successfully")
        print(f"  Version: {pulp.__version__ if hasattr(pulp, '__version__') else 'Unknown'}")
    except ImportError:
        print("✗ PuLP not found. Install with: pip install pulp")
        return False
    
    try:
        import numpy as np
        print("✓ NumPy installed successfully")
        print(f"  Version: {np.__version__}")
    except ImportError:
        print("✗ NumPy not found. Install with: pip install numpy")
        return False
    
    try:
        import matplotlib
        print("✓ Matplotlib installed successfully")
        print(f"  Version: {matplotlib.__version__}")
    except ImportError:
        print("✗ Matplotlib not found. Install with: pip install matplotlib")
        return False
    
    return True

def test_pulp_solver():
    """Test if PuLP can solve a simple problem"""
    print("\nTesting PuLP solver...")
    
    try:
        import pulp
        
        # Create a simple test problem
        prob = pulp.LpProblem("Test", pulp.LpMaximize)
        x = pulp.LpVariable("x", lowBound=0)
        prob += x
        prob += x <= 10
        
        # Solve
        status = prob.solve()
        
        if status == pulp.LpStatusOptimal:
            print("✓ PuLP solver working correctly")
            print(f"  Test solution: x = {x.varValue}")
            return True
        else:
            print("✗ PuLP solver failed")
            return False
            
    except Exception as e:
        print(f"✗ Error testing PuLP solver: {e}")
        return False

def main():
    print("="*60)
    print("Lab 1 - Setup Verification")
    print("="*60)
    
    # Test imports
    if not test_imports():
        print("\n❌ Some packages are missing. Please install them.")
        print("\nRun: pip install pulp matplotlib numpy")
        sys.exit(1)
    
    # Test solver
    if not test_pulp_solver():
        print("\n❌ PuLP solver test failed.")
        sys.exit(1)
    
    print("\n" + "="*60)
    print("✅ All tests passed! You're ready to run the notebook.")
    print("="*60)
    print("\nNext steps:")
    print("1. Open Jupyter: jupyter notebook jure_rajcic.ipynb")
    print("2. Run all cells: Cell → Run All")
    print("3. Review the results and graphs")

if __name__ == "__main__":
    main()
