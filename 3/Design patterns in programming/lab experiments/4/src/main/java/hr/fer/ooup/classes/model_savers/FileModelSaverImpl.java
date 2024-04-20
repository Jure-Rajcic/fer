package hr.fer.ooup.classes.model_savers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.ooup.interfaces.ModelSaver;

public class FileModelSaverImpl implements ModelSaver {

    private static final String MODEL_FOLDER = "data/models/";
    private final String FILE_EXTENSION = ".model";
    private final String fileName;

    public FileModelSaverImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(List<String> data) throws IOException{
        Files.write(Paths.get(MODEL_FOLDER + fileName + FILE_EXTENSION), data);
    }
    
}
