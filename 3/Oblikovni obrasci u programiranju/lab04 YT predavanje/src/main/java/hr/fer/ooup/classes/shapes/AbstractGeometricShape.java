package hr.fer.ooup.classes.shapes;

import hr.fer.ooup.classes.renderers.Renderers;
import hr.fer.ooup.interfaces.GeometricShape;
import hr.fer.ooup.interfaces.Renderer;

public abstract class AbstractGeometricShape implements GeometricShape {

    private boolean selected;
    protected Renderer renderer;

    public AbstractGeometricShape() {
        this.renderer = Renderers.getDefaultRenderer();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }


    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    
}
