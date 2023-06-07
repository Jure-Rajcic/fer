package hr.fer.ooup.classes.states;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import hr.fer.ooup.classes.DocumentModel;
import hr.fer.ooup.classes.shape.CompositeShape;
import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.Renderer;

public class SelectShapeState extends IdleState {

    private DocumentModel document;
    private GraphicalObject selectedObject;

    public SelectShapeState(DocumentModel document) {
        this.document = document;
        this.selectedObject = null;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (selectedObject != null && document.findSelectedHotPoint(selectedObject, mousePoint) != -1)
            return;
        GraphicalObject go = document.findSelectedGraphicalObject(mousePoint);
        if (go == null && !ctrlDown)
            document.list().forEach(g -> g.setSelected(false));
        if (go == null)
            return;
        if (!ctrlDown)
            document.list().forEach(g -> g.setSelected(false));
        // System.out.println("setting line " + go + " selection to: " +
        // !go.isSelected());
        go.setSelected(!go.isSelected());

        selectedObject = go.isSelected() ? go : null;
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (selectedObject == null)
            return;

        int index = document.findSelectedHotPoint(selectedObject, mousePoint);
        if (index == -1)
            return;

        selectedObject.setHotPoint(index, mousePoint);
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if (!go.isSelected())
            return;
        drawBoundingBox(go, r);
        if (document.getSelectedObjects().size() == 1)
            drawHotPoints(go, r);
    }

    private void drawBoundingBox(GraphicalObject go, Renderer r) {
        Rectangle bb = go.getBoundingBox();
        Point topLeft = new Point(bb.x, bb.y);
        Point bottomRight = new Point(bb.x + bb.width, bb.y + bb.height);
        Point topRight = new Point(bb.x + bb.width, bb.y);
        Point bottomLeft = new Point(bb.x, bb.y + bb.height);

        r.drawLine(topLeft, topRight, Color.BLACK);
        r.drawLine(topRight, bottomRight, Color.BLACK);
        r.drawLine(bottomRight, bottomLeft, Color.BLACK);
        r.drawLine(bottomLeft, topLeft, Color.BLACK);
    }

    private void drawHotPoints(GraphicalObject go, Renderer r) {
        final int halfSide = 6;
        for (int i = 0; i < go.getNumberOfHotPoints(); i++) {
            Point p = go.getHotPoint(i);

            Point topLeft = new Point(p.x - halfSide, p.y - halfSide);
            Point topRight = new Point(p.x + halfSide, p.y - halfSide);
            Point bottomLeft = new Point(p.x - halfSide, p.y + halfSide);
            Point bottomRight = new Point(p.x + halfSide, p.y + halfSide);

            Color c = Color.BLACK;
            r.drawLine(topLeft, topRight, c);
            r.drawLine(topRight, bottomRight, c);
            r.drawLine(bottomRight, bottomLeft, c);
            r.drawLine(bottomLeft, topLeft, c);

        }
    }

    // Tipke na mac-u za plus i minus ???
    private static final int PLUS = 61;
    private static final int MINUS = 47;

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT: {
                moveSelection(5, 0);
                break;
            }
            case KeyEvent.VK_LEFT: {
                moveSelection(-5, 0);
                break;
            }
            case KeyEvent.VK_UP: {
                moveSelection(0, -5);
                break;
            }
            case KeyEvent.VK_DOWN: {
                moveSelection(0, 5);
                break;
            }
            case PLUS: {
                if (document.getSelectedObjects().size() == 1)
                    document.getSelectedObjects().forEach(document::increaseZ);
                break;
            }
            case MINUS: {
                if (document.getSelectedObjects().size() == 1)
                    document.getSelectedObjects().forEach(document::decreaseZ);
                break;
            }
            case KeyEvent.VK_G: {
                groupSelection();
                break;
            }
            case KeyEvent.VK_U: {
                unGroupSelection();
                break;
            }
            default:
                System.out.println("default key pressed: " + keyCode);
                break;
        }
    }

    private void moveSelection(int dx, int dy) {
        document.getSelectedObjects().forEach(go -> go.translate(new Point(dx, dy)));
    }

    private void groupSelection() {
        // System.out.println("grouping selection");
        List<GraphicalObject> selectedShapes = document.getSelectedObjects();
        if (selectedShapes.size() < 2)
            return;

        selectedShapes.forEach(document::removeGraphicalObject);

        CompositeShape group = new CompositeShape(selectedShapes, true);
        document.addGraphicalObject(group);
        selectedObject = group;
    }

    private void unGroupSelection() {
        // System.out.println("ungrouping selection");
        if (selectedObject == null || !(selectedObject instanceof CompositeShape))
            return;
        CompositeShape group = (CompositeShape) selectedObject;
        group.getGraphicalObjects().forEach(document::addGraphicalObject);
        document.removeGraphicalObject(group);
    }

    @Override
    public void onLeaving() {
        document.list().forEach(g -> g.setSelected(false));
    }

}
