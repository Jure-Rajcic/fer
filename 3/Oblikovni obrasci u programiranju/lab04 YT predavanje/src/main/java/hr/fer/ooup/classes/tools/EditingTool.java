package hr.fer.ooup.classes.tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.ooup.classes.GUI.Canvas;
import hr.fer.ooup.classes.shapes.Group;
import hr.fer.ooup.interfaces.GeometricShape;

public class EditingTool extends AbstractTool {

    public EditingTool(List<GeometricShape> document, Canvas canvas) {
        super(document, canvas);
    }

    @Override
    public void stateDeactivated() {
        document.forEach(g -> g.setSelected(false));
        canvas.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                moveSelection(5, 0);
                e.consume();
                break;
            case KeyEvent.VK_LEFT:
                moveSelection(-5, 0);
                e.consume();
                break;
            case KeyEvent.VK_UP:
                moveSelection(0, -5);
                e.consume();
                break;
            case KeyEvent.VK_DOWN:
                moveSelection(0, 5);
                e.consume();
                break;
            case KeyEvent.VK_G:
                groupSelection();
                e.consume();
                break;
            case KeyEvent.VK_U:
                unGroupSelection();
                e.consume();
                break;
        }
    }

    private void moveSelection(int dx, int dy) {
        document.stream()
                .filter(GeometricShape::isSelected)
                .forEach(g -> g.move(dx, dy));
        canvas.repaint();
    }

    private void groupSelection() {
        List<GeometricShape> selectedShapes = document.stream()
                .filter(GeometricShape::isSelected)
                .collect(Collectors.toList());
        if (selectedShapes.size() < 2)
            return;

        document.removeIf(selectedShapes::contains);
        document.add(new Group(selectedShapes, true));
        canvas.repaint();
    }

    private void unGroupSelection() {
        List<Group> selectedGroups = document.stream()
                .filter(g -> g instanceof Group)
                .map(g -> (Group) g)
                .filter(Group::isSelected)
                .collect(Collectors.toList());

        document.removeIf(selectedGroups::contains);

        selectedGroups.stream()
                .flatMap(g -> g.getShapes().stream())
                .forEach(document::add);

        canvas.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (document.isEmpty())
            return;
        GeometricShape closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (GeometricShape shape : document) {
            double distance = shape.distanceFromPoint(e.getPoint());
            if (closest == null || distance < closestDistance) {
                closest = shape;
                closestDistance = distance;
            }
        }
        if (closestDistance > 10) {
            if (!e.isControlDown()) {
                document.forEach(g -> g.setSelected(false));
                canvas.repaint();
            }
            return;
        }
        if (e.isControlDown()) {
            closest.setSelected(!closest.isSelected());
        } else {
            document.forEach(g -> g.setSelected(false));
            closest.setSelected(true);
        }
        canvas.repaint();
    }

}
