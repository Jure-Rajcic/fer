import java.io.PrintWriter;
import java.nio.file.Path;

public final class TimeoutFileWriter extends FileWriter {

    private final long timeoutMs;

    public TimeoutFileWriter(Path inputPath, long timeoutMs) {
        super(inputPath);
        this.timeoutMs = timeoutMs;
    }

    @Override
    protected void writeToFile(PrintWriter printWriter) {
        double timeoutSeconds = timeoutMs / 1000.0;
        printWriter.println("c TIMEOUT after " + timeoutSeconds + " s");
    }
}
