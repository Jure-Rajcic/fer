import java.util.List;

public final class CnfFormula {

    public final int numVars;
    public final List<int[]> clauses;

    public CnfFormula(int numVars, List<int[]> clauses) {
        this.numVars = numVars;
        this.clauses = clauses;
    }

    public int numClauses() {
        return clauses.size();
    }
}
