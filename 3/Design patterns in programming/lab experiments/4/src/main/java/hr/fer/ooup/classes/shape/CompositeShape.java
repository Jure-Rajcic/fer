package hr.fer.ooup.classes.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.Renderer;

public class CompositeShape extends AbstractGraphicalObject {

    private List<GraphicalObject> graphicalObjects;


    public CompositeShape(List<GraphicalObject> graphicalObjects, boolean selected) {
        super(new Point[] {});
        this.graphicalObjects = graphicalObjects;
        setSelected(selected);
    }


    @Override
    public void setSelected(boolean selected) {
        graphicalObjects.forEach(o -> o.setSelected(selected));
        super.setSelected(selected);
    }


    @Override
    public void translate(Point delta) {
        graphicalObjects.forEach(o -> o.translate(delta));
    }

    @Override
    public Rectangle getBoundingBox() {
        Rectangle boundingBox = null;
        for (GraphicalObject go : graphicalObjects) {
            Rectangle bb = go.getBoundingBox();
            if (boundingBox == null) {
                boundingBox = bb;
            } else {
                boundingBox = boundingBox.union(bb);
            }
        }
        return boundingBox;
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        double minDistance = Double.MAX_VALUE;
        for (GraphicalObject o : graphicalObjects) {
            double distance = o.selectionDistance(mousePoint);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    @Override
    public void render(Renderer r) {
        graphicalObjects.forEach(o -> o.render(r));
    }

    @Override
    public String getShapeName() {
        return "Composite";
    }

    public List<GraphicalObject> getGraphicalObjects() {
        return graphicalObjects;
    }


    @Override
    public GraphicalObject duplicate() {
        return new CompositeShape(graphicalObjects, isSelected());
    }




    @Override
    public Point getHotPoint(int index) {
        throw new UnsupportedOperationException("while calling getHotPoint(int) because Composite shape does not have hot points");
    }


    @Override
    public void setHotPoint(int index, Point point) {
        throw new UnsupportedOperationException("while calling setHotPoint(int, Point) because Composite shape does not have hot points");

    }

    @Override
    public boolean isHotPointSelected(int index) {
        throw new UnsupportedOperationException("while calling isHotPointSelected(int) because Composite shape does not have hot points");
    }


    @Override
    public void setHotPointSelected(int index, boolean selected) {
        throw new UnsupportedOperationException("while calling setHotPointSelected(int, boolean) because Composite shape does not have hot points");
    }


    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        throw new UnsupportedOperationException("while calling getHotPointDistance(int, Point) because Composite shape does not have hot points");
    }


    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void save(List<String> rows) {
        for (GraphicalObject go : graphicalObjects) {
            go.save(rows);
        }
        rows.add(getShapeID() + " " + graphicalObjects.size());
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] parts = data.split(" ");
        int n = Integer.parseInt(parts[1]);
        List<GraphicalObject> objects = new ArrayList<>();
        while (n != 0) {
            GraphicalObject go = stack.pop();
            objects.add(go);
            n--;
        }
        CompositeShape compositeShape = new CompositeShape(objects, false);
        stack.push(compositeShape);
    }

    
}
