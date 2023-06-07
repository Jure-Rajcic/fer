package hr.fer.ooup.classes.tools;

import java.awt.Graphics2D;
import java.util.List;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import hr.fer.ooup.classes.GUI.Canvas;
import hr.fer.ooup.classes.shapes.Circle;
import hr.fer.ooup.interfaces.GeometricShape;

public class AddCircleTool extends AbstractTool {

    private Circle circle;

    public AddCircleTool(List<GeometricShape> document, Canvas canvas) {
        super(document, canvas);
    }

    @Override
    public void stateActivated() {
        circle = null;
    }


    @Override
    public void draw(Graphics2D g) {
        if (circle == null)
            return;
        circle.draw();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            circle = null;
            canvas.repaint();
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        circle = new Circle(e.getPoint(), 1);
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (circle == null)
            return;
        document.add(circle);
        circle = null;
        canvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (circle == null)
            return;
        int dx = e.getX() - circle.getCenter().x;
        int dy = e.getY() - circle.getCenter().y;
        int r = (int) (Math.sqrt(dx * dx + dy * dy) + 0.5);
        circle.setRadius(Math.max(1, r));
        canvas.repaint();
    }

}
