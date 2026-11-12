import java.io.PrintWriter;
import java.nio.file.Path;

public final class SatFileWriter extends FileWriter {

    private final CnfFormula formula;
    private final LiteralValue[] assignment;

    public SatFileWriter(Path inputPath, CnfFormula formula, LiteralValue[] assignment) {
        super(inputPath);
        this.formula = formula;
        this.assignment = assignment;
    }

    @Override
    protected void writeToFile(PrintWriter printWriter) {
        for (int variable = 1; variable <= formula.numVars; variable++) {
            boolean val = switch (assignment[variable]) {
                case TRUE -> true;
                case FALSE -> false;
                case UNASSIGNED -> true;
            };
            printWriter.println("v" + variable + " = " + val);
        }
    }
}
