package hr.fer.ooup.classes.tools;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import hr.fer.ooup.classes.GUI.Canvas;
import hr.fer.ooup.classes.shapes.Square;
import hr.fer.ooup.interfaces.GeometricShape;

public class AddSquareTool extends AbstractTool {

    private Square square;

    public AddSquareTool(List<GeometricShape> document, Canvas canvas) {
        super(document, canvas);
    }

    @Override
    public void stateActivated() {
        square = null;
    }


    @Override
    public void draw(Graphics2D g) {
        if (square != null)
            square.draw();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            square = null;
            canvas.repaint();
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        square = new Square(e.getPoint(), 1);
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (square != null) {
            document.add(square);
            square = null;
            canvas.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (square == null)
            return;
        int dx = e.getX() - square.getTopLeft().x;
        int dy = e.getY() - square.getTopLeft().y;
        int side = Math.max(Math.abs(dx), Math.abs(dy));
        side = Math.max(side, 1);
        dx = dx < 0 ? -side : side;
        dy = dy < 0 ? -side : side;

        int x1 = square.getTopLeft().x;
        int x2 = x1 + dx;

        int y1 = square.getTopLeft().y;
        int y2 = y1 + dy;

        square.getTopLeft().x = Math.min(x1, x2);
        square.getTopLeft().y = Math.min(y1, y2);
        square.setSide(Math.abs(side));
        canvas.repaint();
    }

}
