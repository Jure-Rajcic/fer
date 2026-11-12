import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.stream.IntStream;


public final class DpllSolver implements SolverI {

    private final CnfFormula formula;
    private final LiteralValue[] value;
    private long deadlineNs;

    public DpllSolver(CnfFormula formula, long timeoutMs) {
        this.formula = formula;
        this.value = new LiteralValue[formula.numVars + 1];
        this.deadlineNs = System.nanoTime() + timeoutMs * 1_000_000L;
        Arrays.fill(this.value, LiteralValue.UNASSIGNED);
    }

    private boolean timeExceeded() {
        return System.nanoTime() >= deadlineNs;
    }

    private LiteralValue positiveLiteralValue(LiteralValue literalValue) {
        return switch (literalValue) {
            case TRUE -> LiteralValue.TRUE;
            case FALSE -> LiteralValue.FALSE;
            case UNASSIGNED -> LiteralValue.UNASSIGNED;
        };
    }

    private LiteralValue negatedLiteralValue(LiteralValue literalValue) {
        return switch (literalValue) {
            case TRUE -> LiteralValue.FALSE;
            case FALSE -> LiteralValue.TRUE;
            case UNASSIGNED -> LiteralValue.UNASSIGNED;
        };
    }


    private final Deque<Integer> trail = new ArrayDeque<>(); // LIFO
    private void assignLit(int lit) {
        int index = Math.abs(lit);
        boolean isLiteralNegative = lit < 0;
        if (isLiteralNegative) value[index] = LiteralValue.FALSE;
        else value[index] = LiteralValue.TRUE;
        trail.push(lit);
    }

    private LiteralValue literalValue(int lit) {
        int index = Math.abs(lit);
        LiteralValue literalValue = value[index];
        boolean isLiteralNegative = lit < 0;
        if (isLiteralNegative) return negatedLiteralValue(literalValue);
        else return positiveLiteralValue(literalValue);
    }



    private boolean isClauseSatisfied(int[] clause) {
        return Arrays.stream(clause).anyMatch(lit -> literalValue(lit) == LiteralValue.TRUE);
    }

    private int getNumberOfUnassignedLiterals(int[] clause) {
        return (int) Arrays.stream(clause).filter(lit -> literalValue(lit) == LiteralValue.UNASSIGNED).count();
    }

    private int getUnassignedLiteral(int[] clause) {
        return Arrays.stream(clause).filter(lit -> literalValue(lit) == LiteralValue.UNASSIGNED).findFirst().orElse(0);
    }

    private PropagationResult unitClausePropagation() {
        PropagationResult pRes = PropagationResult.NO_CHANGE;
        for (int[] clause : formula.clauses) {
            if (isClauseSatisfied(clause)) continue;
            int unassignedCount = getNumberOfUnassignedLiterals(clause);
            if (unassignedCount == 0) return PropagationResult.CONFLICT;
            if (unassignedCount != 1) continue;

            int unassignedLit = getUnassignedLiteral(clause);
            assignLit(unassignedLit);
            pRes = PropagationResult.CHANGED;
        }
        return pRes;
    }


    private LiteralEliminationResult pureLiteralElimination() {
        LiteralEliminationResult leRes = LiteralEliminationResult.NO_CHANGE;
        boolean[] seenPos = new boolean[formula.numVars + 1];
        boolean[] seenNeg = new boolean[formula.numVars + 1];

        // Prvo pronađi u kojim se polaritetima pojavljuju nepridijeljene varijable
        for (int[] clause : formula.clauses) {
            if (isClauseSatisfied(clause)) continue;
            for (int lit : clause) {
                var index = Math.abs(lit);
                boolean isLiteralNegated = lit < 0;
                if (isLiteralNegated) seenNeg[index] = true;
                else seenPos[index] = true;
            }
        }

         int before = trail.size();

    java.util.stream.IntStream.rangeClosed(1, formula.numVars)
            .filter(var -> value[var] == LiteralValue.UNASSIGNED)
            .filter(var -> seenPos[var] && !seenNeg[var])
            .forEach(var -> assignLit(var));

    java.util.stream.IntStream.rangeClosed(1, formula.numVars)
            .filter(var -> value[var] == LiteralValue.UNASSIGNED)
            .filter(var -> !seenPos[var] && seenNeg[var])
            .forEach(var -> assignLit(-var));

    if(trail.size() > before) 
        return LiteralEliminationResult.CHANGED;
        else return LiteralEliminationResult.NO_CHANGE;
    }


    private FormulaStatus evaluateFormula() {
        boolean undecided = false;
        for (int[] clause : formula.clauses) {
            if (isClauseSatisfied(clause)) continue;
            int unassignedCount = getNumberOfUnassignedLiterals(clause);
            if (unassignedCount == 0) return FormulaStatus.UNSATISFIABLE;
            undecided = true;
        }
        if (undecided) return FormulaStatus.UNDECIDED;
        else return FormulaStatus.SATISFIABLE;
    }

    private void backtrackTo(int mark) {
        while (trail.size() > mark) {
            int lit = trail.pop();
            int index = Math.abs(lit);
            value[index] = LiteralValue.UNASSIGNED;
        }
    }


    private int countLiteralInUnsatisfiedClauses(int searchedLit) {
        return (int) formula.clauses.stream()
                .filter(clause -> !isClauseSatisfied(clause))
                .filter(clause -> Arrays.stream(clause)
                    .anyMatch(lit -> lit == searchedLit && literalValue(lit) == LiteralValue.UNASSIGNED)
                )
                .count();
}

    private record LiteralScore(int lit, int count) {}
    // Za svaki nepridijeljeni literal l izračunaj Cl = broj nezadovoljenih klauzula u kojima 
    // se l trenutno pojavljuje. Odaberi literal s najvećim Cl. Varijabla tog literala postaje 
    // sljedeća varijabla grananja, a njena prva pridijeljena vrijednost je ona koja literal 
    // čini istinitim.
    private int chooseBranchLiteral() {
        return IntStream.rangeClosed(1, formula.numVars)
                .filter(var -> value[var] == LiteralValue.UNASSIGNED)
                .flatMap(var -> IntStream.of(var, -var))
                .mapToObj(lit -> new LiteralScore(lit, countLiteralInUnsatisfiedClauses(lit)))
                .max(Comparator.comparingInt(LiteralScore::count))
                .map(LiteralScore::lit)
                .orElseThrow();
    }



    public SolveStatus solve() {
        if (timeExceeded()) return SolveStatus.TIMEOUT;
        int mark = trail.size();

        // 1) Simplifikacija: PJK + UKCL
        while (!timeExceeded()) {
            LiteralEliminationResult pureLiteralEliminationRes = pureLiteralElimination();
            PropagationResult unitClausePropagationRes = unitClausePropagation();
            if (unitClausePropagationRes == PropagationResult.CONFLICT) {
                backtrackTo(mark);
                return SolveStatus.UNSATISFIABLE;
            }
            boolean pureLiteralEliminationChanged = pureLiteralEliminationRes == LiteralEliminationResult.CHANGED;
            boolean unitClausePropagationChanged = unitClausePropagationRes == PropagationResult.CHANGED;
            if (!(pureLiteralEliminationChanged || unitClausePropagationChanged)) break;
        }


        // 2) Evaluacija formule: 
        FormulaStatus fs = evaluateFormula();
        if (fs == FormulaStatus.SATISFIABLE) return SolveStatus.SATISFIABLE;
        if (fs == FormulaStatus.UNSATISFIABLE) {
            backtrackTo(mark);
            return SolveStatus.UNSATISFIABLE;
        }


        // 4) Grananje (DLIS heuristika).
        int branchMark = trail.size();
        int branchLit = chooseBranchLiteral();

        // Case 1: branchLit = true.
        assignLit(branchLit);
        SolveStatus solveStatus = solve();
        switch (solveStatus) {
            case SATISFIABLE: return solveStatus;
            case TIMEOUT: return SolveStatus.TIMEOUT;
            case UNSATISFIABLE: backtrackTo(branchMark); break;
        }

        // Case 2: branchLit = false.
        assignLit(-branchLit);
        SolveStatus solveStatus2 = solve();
        switch (solveStatus2) {
            case SATISFIABLE: return solveStatus2;
            case TIMEOUT: return SolveStatus.TIMEOUT;
            case UNSATISFIABLE: backtrackTo(mark); break;
        }

        // Both cases failed -> UNSAT.
        return SolveStatus.UNSATISFIABLE;
    }

    @Override
    public LiteralValue[] getAssignment() { return value; }



}
