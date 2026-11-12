# Formal Software Verification 📂

### 📖 Course Overview
Mathematically-grounded methods for specification, development, and verification of software systems aimed at improving software product quality while reducing time-to-market. Temporal logic and its formalisms (LTL, CTL). Specification and verification of computational and reactive systems. Formal verification of real-world software programs. Principles of abstraction and symbolic verification. Verification algorithms via theorem proving and model checking: Binary Decision Diagrams (BDD), SAT solvers, and SMT solvers. Hoare logic and program correctness proofs. Automated formal verification tools and frameworks. Applications across diverse domains of software engineering.

### 🔬 Lab Experiments (🇭🇷)
Hands-on laboratory assignments exploring formal model checking and automated theorem proving / SAT solving:
- **[Lab 1 - NuSMV Model Checking](file:///Users/jurerajcic/Desktop/fer/5/Formal%20Software%20Verification/lab%20experiments/lab1_nusmv)**:
  - System specification, state transitions, and temporal logic property verification using **NuSMV 2.6.0** (`FVPP_NuSMV_upute.pdf`).
  - Solved verification models:
    - Mutual exclusion algorithms (`mutex_1ex.smv`, `mutex_1ex_int.smv`, `mutex_2ex.smv`, `mutex_3ex.smv`)
    - The Ferryman river crossing puzzle (`ferryman.smv`)
    - Tic-Tac-Toe game state space exploration and winning strategy verification (`tic_tac_toe.smv`)
- **[Lab 3 - DPLL SAT Solver](file:///Users/jurerajcic/Desktop/fer/5/Formal%20Software%20Verification/lab%20experiments/lab3_sat)**:
  - Implementation of the **DPLL (Davis-Putnam-Logemann-Loveland)** algorithm for propositional Boolean satisfiability (`FVPP_DZ3.pdf`, `9_SAT_rjesavaci.pdf`).
  - DIMACS CNF benchmark evaluation and performance analysis (`satformat.pdf`, `DZ3-benchmarks`).

