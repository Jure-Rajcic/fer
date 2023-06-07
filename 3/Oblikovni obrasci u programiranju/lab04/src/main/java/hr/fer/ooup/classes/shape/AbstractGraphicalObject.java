package hr.fer.ooup.classes.shape;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.GraphicalObjectListener;

public abstract class AbstractGraphicalObject implements GraphicalObject {

    private boolean selected;
    private Point[] hotPoints;
    private boolean[] hotPointSelected;
    private List<GraphicalObjectListener> listeners;

    public AbstractGraphicalObject(Point[] hotPoints) {
        this.selected = false;
        this.hotPoints = hotPoints;
        this.hotPointSelected = new boolean[hotPoints.length];
        listeners = new ArrayList<>();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        if (this.selected == selected)
            return;
        this.selected = selected;
        notifySelectionListeners();
    }

    @Override
    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    @Override
    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    @Override
    public void setHotPoint(int index, Point point) {
        hotPoints[index] = point;
        notifyListeners();
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected[index] = selected;
        notifySelectionListeners();
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return hotPoints[index].distance(mousePoint);
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    protected void notifyListeners() {
        listeners.forEach(l -> l.graphicalObjectChanged(this));
    }

    protected void notifySelectionListeners() {
        listeners.forEach(l -> l.graphicalObjectSelectionChanged(this));
    }

    @Override
    public void translate(Point delta) {
        for (Point p : hotPoints) {
            p.translate(delta.x, delta.y);
        }
        notifyListeners();
    }

}
