package hr.fer.ooup.classes.tools;

import java.util.List;

import hr.fer.ooup.classes.GUI.Canvas;
import hr.fer.ooup.interfaces.GeometricShape;

public abstract class AbstractTool extends ToolAdapter {

    protected List<GeometricShape> document;
    protected Canvas canvas;

    public AbstractTool(List<GeometricShape> document, Canvas canvas) {
        this.document = document;
        this.canvas = canvas;
    }
    
}
