import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Main {

    private static int TIMEOUT_MS = 60 * 1000;

    
    public static void main(String[] args) throws IOException {
        // 1) Pokreni sve .cnf datoteke iz benchmarks direktorija (tablica)
        runAll("../DZ3-benchmarks", SolverType.DPLL);
        // runAll("../DZ3-benchmarks", SolverType.CDCL);

        // 2) Pokreni jednu konkretnu datoteku (pun ispis)
        // runSingle("../DZ3-benchmarks/hanoi4.cnf",  SolverType.DPLL);
        // runSingle("../DZ3-benchmarks/hanoi4.cnf", SolverType.CDCL);
    }

    
    public static void runAll(String cnfDir, SolverType st) throws IOException {
        Path dir = Paths.get(cnfDir);
        if (!Files.isDirectory(dir)) 
        throw new IOException("Not a directory: " + dir.toAbsolutePath());
        
        DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.cnf");
        for (Path p : ds) runSingle(p.toString(), st);
        ds.close();
    }

   
    public static void runSingle(String cnfPath, SolverType st) throws IOException {
        Path inputPath = Paths.get(cnfPath);
        if (!Files.isRegularFile(inputPath)) 
            throw new IOException("File does not exist: " + inputPath.toAbsolutePath());
        
        System.out.println(inputPath.getFileName() + ": Running " + st);
        final var t0 = System.nanoTime();
        CnfFormula formula = DimacsParser.parse(inputPath);
        final var t1 = System.nanoTime();
        System.out.println(" ".repeat(4) + "Parsing time: " + (t1 - t0) / 1_000_000.0 + " ms");
        SolverI solver = null;
        switch (st) {
            case DPLL ->  solver = new DpllSolver(formula, TIMEOUT_MS); 
            // case CDCL ->  solver = new CdclSolver(formula, TIMEOUT_MS); 
            default -> throw new IllegalStateException("Unknown SolverType: " + st);
        };
        SolveStatus result = solver.solve();
        final var t2 = System.nanoTime();
        System.out.println(" ".repeat(4) + "Solving time: " + (t2 - t1) / 1_000_000.0 + " ms");

        System.out.println(" ".repeat(4) + "Result: " + result);
        FileWriter writer;
        switch (result) {
            case SATISFIABLE     -> writer = new SatFileWriter(inputPath, formula, solver.getAssignment());
            case UNSATISFIABLE   -> writer = new UnsatFileWriter(inputPath);
            case TIMEOUT -> writer = new TimeoutFileWriter(inputPath, TIMEOUT_MS);
            default      -> throw new IllegalStateException("Unknown SolveStatus: " + result);
        };
        writer.createFile();
    }
}


// jurerajcic@Jures-MacBook-Pro dpll-sat % javac -d out src/Main.java src/DimacsParser.java src/FileWriter.java src/1/DpllSolver.java src/enums/*.java src/model/CnfFormula.java src/output/*.java src/interfaces/*.java

// jurerajcic@Jures-MacBook-Pro dpll-sat % java -cp out Main
