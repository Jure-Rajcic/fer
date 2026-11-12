import java.io.PrintWriter;
import java.nio.file.Path;

public final class UnsatFileWriter extends FileWriter {

    public UnsatFileWriter(Path inputPath) {
        super(inputPath);
    }

    @Override
    protected void writeToFile(PrintWriter printWriter) {
        printWriter.println("false");
    }
}
