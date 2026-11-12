import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class DimacsParser {

    private DimacsParser() {}

    public static CnfFormula parse(Path path) throws IOException {
        CnfFormula formula = null;

        BufferedReader reader = Files.newBufferedReader(path);
        String line;

        while ((line = reader.readLine()) != null) {
            if (isComment(line)) continue;
            if (!isSummary(line)) throw new IOException("I expected summary after comments..");
            formula = parseSummary(line);
            break;
        }

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) continue;
            if (line.trim().startsWith("%")) break;
            if (isComment(line)) continue;
            if (isSummary(line)) throw new IOException("I expected clause after summary..");
            int[] clause = parseClause(line);
            if (clause.length == 0) continue;
            formula.clauses.add(clause);
        }
        reader.close();
        return formula;
    }

    public static boolean isComment(String line) {
        StringTokenizer tk = new StringTokenizer(line);
        if (!tk.hasMoreTokens()) return false;
        String first = tk.nextToken();
        return first.equalsIgnoreCase("c");
    }

    public static boolean isSummary(String line) {
        StringTokenizer tk = new StringTokenizer(line);
        if (!tk.hasMoreTokens()) return false;
        String first = tk.nextToken();
        return first.equalsIgnoreCase("p");
    }

    public static CnfFormula parseSummary(String line) throws IOException {
        StringTokenizer tk = new StringTokenizer(line);
        String first = tk.nextToken();
        if (!first.equalsIgnoreCase("p")) throw new IOException("Ocekivana " + first + " linija u DIMACS formatu");
        String fmt = tk.nextToken();
        if (!fmt.equalsIgnoreCase("cnf")) throw new IOException("Nepodrzani format: " + fmt + " (ocekivano 'cnf')");
        String num = tk.nextToken();
        return new CnfFormula(Integer.parseInt(num), new ArrayList<int[]>());   
    }

    public static int[] parseClause(String line) {
        StringTokenizer tk = new StringTokenizer(line);
        int[] tokens = new int[tk.countTokens()];
        int idx = 0;
        while (tk.hasMoreTokens()) {
            int lit = Integer.parseInt(tk.nextToken());
            if (lit == 0) break;
            tokens[idx++] = lit;
        }
        int[] clause = new int[idx];
        System.arraycopy(tokens, 0, clause, 0, idx);
        return clause;
    }
}
