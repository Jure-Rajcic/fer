package hr.fer.ooup.classes.model_loders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.ooup.classes.shape.CompositeShape;
import hr.fer.ooup.classes.shape.LineSegment;
import hr.fer.ooup.classes.shape.Oval;
import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.ModelLoader;

public class FileModelLoaderImpl implements ModelLoader {

    private static final String MODEL_FOLDER = "data/models/";
    private final String FILE_EXTENSION_TYPE = "model";
    
    private String fileName;
    

    private final Map<String, GraphicalObject> prototypes = new HashMap<>();
    {
        LineSegment lineSegment = new LineSegment();
        prototypes.put(lineSegment.getShapeID(), lineSegment);

        Oval oval = new Oval();
        prototypes.put(oval.getShapeID(), oval);

        CompositeShape compositeShape = new CompositeShape(new ArrayList<>(), false);
        prototypes.put(compositeShape.getShapeID(), compositeShape);

    }

    public String selectFile() {
        JFileChooser fileChooser = new JFileChooser(new File(MODEL_FOLDER));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Model Files", FILE_EXTENSION_TYPE));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getName();
        }
        return null;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load(Stack<GraphicalObject> stack) throws IOException {
        Scanner sc = new Scanner(new File(MODEL_FOLDER + fileName));
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            String id = parts[0];
            GraphicalObject prototype = prototypes.get(id);
            if (prototype == null) {
                throw new IllegalArgumentException("Prototype with id " + id + " does not exist!");
            }
            prototype.load(stack, line);
        }
    }
  

}
