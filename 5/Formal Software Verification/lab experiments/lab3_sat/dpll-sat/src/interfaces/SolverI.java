public interface SolverI {
    public SolveStatus solve();
    public LiteralValue[] getAssignment();
}
