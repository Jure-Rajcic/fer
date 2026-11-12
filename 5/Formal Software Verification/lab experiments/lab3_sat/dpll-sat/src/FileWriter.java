import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileWriter {

    private final Path inputPath;
    private final Path outputPath;

    public FileWriter(Path inputPath) {
        this.inputPath = inputPath;
        this.outputPath = createOutputPath(inputPath);
    }

    private Path createOutputPath(Path inputPath) {
        String inputFileName = inputPath.getFileName().toString();

        int extensionIndex = inputFileName.lastIndexOf('.');
        String outputFileBaseName;

        if (extensionIndex == -1) {
            outputFileBaseName = inputFileName;
        } else {
            outputFileBaseName = inputFileName.substring(0, extensionIndex);
        }

        Path inputParentDirectory = inputPath.toAbsolutePath().getParent();
        String outputFileName = outputFileBaseName + ".txt";

        return inputParentDirectory.resolve(outputFileName);
    }

    public final void createFile() throws IOException {
        var charset = StandardCharsets.UTF_8;

        BufferedWriter bufferedWriter = Files.newBufferedWriter(outputPath, charset);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        try {
            writeToFile(printWriter);
        } finally {
            printWriter.close();
        }
    }

    protected abstract void writeToFile(PrintWriter printWriter);
}