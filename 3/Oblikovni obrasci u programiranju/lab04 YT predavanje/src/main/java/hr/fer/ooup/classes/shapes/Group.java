package hr.fer.ooup.classes.shapes;

import java.awt.Color;
import java.util.List;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.ooup.interfaces.GeometricShape;

public class Group extends AbstractGeometricShape {

    private List<GeometricShape> shapes;

    public Group(List<GeometricShape> shapes, boolean selected) {
        this.shapes = shapes;
        setSelected(selected);
    }

    public List<GeometricShape> getShapes() {
        return shapes;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        shapes.forEach(shape -> shape.setSelected(selected));
    }

    @Override
    public void draw() {
        shapes.forEach(shape -> shape.draw());
        if (!isSelected()) return;

        Rectangle bb = calculateBoundingBox();
        float pattern[] = {5, 3};
        Color color = Color.ORANGE;

        renderer.drawLineDashed(bb.x, bb.y, bb.x + bb.width, bb.y, color, pattern);
        renderer.drawLineDashed(bb.x + bb.width, bb.y, bb.x + bb.width, bb.y + bb.height, color, pattern);
        renderer.drawLineDashed(bb.x + bb.width, bb.y + bb.height, bb.x, bb.y + bb.height, color, pattern);
        renderer.drawLineDashed(bb.x, bb.y + bb.height, bb.x, bb.y, color, pattern);
    }

    @Override
    public void move(int dx, int dy) {
        shapes.forEach(shape -> shape.move(dx, dy));
    }

    @Override
    public double distanceFromPoint(Point p) {
        Double distance = Double.MAX_VALUE;
        for (GeometricShape shape : shapes) {
            distance = Math.min(distance, shape.distanceFromPoint(p));
        }
        return distance;
    }

    @Override
    public Rectangle calculateBoundingBox() {
        Rectangle boundingBox = null;
        for (GeometricShape shape : shapes) {
            Rectangle shapeBoundingBox = shape.calculateBoundingBox();
            if (boundingBox == null) {
                boundingBox = shapeBoundingBox;
            } else {
                boundingBox = boundingBox.union(shapeBoundingBox);
            }
        }
        return boundingBox;
    }
    
}
