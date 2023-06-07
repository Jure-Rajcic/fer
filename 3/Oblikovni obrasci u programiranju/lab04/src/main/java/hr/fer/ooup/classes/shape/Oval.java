package hr.fer.ooup.classes.shape;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Stack;

import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.Renderer;

public class Oval extends AbstractGraphicalObject {


    public Oval() {
        this(new Point[] {new Point(0, 10), new Point(10, 0)});
    }

    public Oval(Point[] hotPoints) {
        super(hotPoints);
    }

    private int getWidth() {
        return Math.abs(getHotPoint(0).x - getHotPoint(1).x);
    }

    private int getHeight() {
        return Math.abs(getHotPoint(0).y - getHotPoint(1).y);
    }

    private Point getCenter() {
        Point hp0 = getHotPoint(0);
        Point hp1 = getHotPoint(1);
        Point center = new Point((hp0.x + hp1.x) / 2, (hp0.y + hp1.y) / 2);
        return center;
    }


    @Override
    public void render(Renderer renderer) {
        final Point center = getCenter();
        final int width = getWidth();
        final int height = getHeight();
        Point topLeft = new Point(center.x - width / 2, center.y - height / 2);
        Point bottomRight = new Point(center.x + width / 2, center.y + height / 2);


        int a = Math.abs(bottomRight.x - topLeft.x) / 2;  // Half of the width
        int b = Math.abs(bottomRight.y - topLeft.y) / 2;  // Half of the height
        int x0 = (bottomRight.x + topLeft.x) / 2;  // Center x
        int y0 = (bottomRight.y + topLeft.y) / 2;  // Center y

        int a2 = a * a;
        int b2 = b * b;

        // First quadrant
        int x = 0, y = b, sigma = 2 * b2 + a2 * (1 - 2 * b);
        while (b2 * x <= a2 * y) {
            Point p1 = new Point(x0 + x, y0 + y);
            Point p2 = new Point(x0 - x, y0 + y);
            Point p3 = new Point(x0 + x, y0 - y);
            Point p4 = new Point(x0 - x, y0 - y);
            
            renderer.drawLine(p1, p2, Color.BLUE);  // Top half
            renderer.drawLine(p3, p4, Color.BLUE);  // Bottom half

            renderer.drawLine(p1, p1, Color.RED);
            renderer.drawLine(p2, p2, Color.RED);
            renderer.drawLine(p3, p3, Color.RED);
            renderer.drawLine(p4, p4, Color.RED);
            if (sigma >= 0) {
                sigma += 4 * a2 * (1 - y);
                y--;
            }
            sigma += b2 * ((4 * x) + 6);
            x++;
        }

        // Second quadrant
        x = a; y = 0; sigma = 2 * a2 + b2 * (1 - 2 * a);
        while (a2 * y <= b2 * x) {
            Point p1 = new Point(x0 + x, y0 + y);
            Point p2 = new Point(x0 - x, y0 + y);
            Point p3 = new Point(x0 + x, y0 - y);
            Point p4 = new Point(x0 - x, y0 - y);
            
            renderer.drawLine(p1, p2, Color.BLUE);  // Top half
            renderer.drawLine(p3, p4, Color.BLUE);  // Bottom half

            renderer.drawLine(p1, p1, Color.RED);
            renderer.drawLine(p2, p2, Color.RED);
            renderer.drawLine(p3, p3, Color.RED);
            renderer.drawLine(p4, p4, Color.RED);

            if (sigma >= 0) {
                sigma += 4 * b2 * (1 - x);
                x--;
            }
            sigma += a2 * ((4 * y) + 6);
            y++;
        }
    }
    

    @Override
    public Rectangle getBoundingBox() {
        final Point center = getCenter();
        final int width = getWidth();
        final int height = getHeight();
        Point topLeft = new Point(center.x - width / 2, center.y - height / 2);
        return new Rectangle(topLeft.x, topLeft.y, width, height);
    }


    @Override
    public double selectionDistance(Point mousePoint) {
        final Point center = getCenter();
        final int width = getWidth();
        final int height = getHeight();

        Point topLeft = new Point(center.x - width / 2, center.y - height / 2);
        Point bottomRight = new Point(center.x + width / 2, center.y + height / 2);

        if (mousePoint.x < topLeft.x || mousePoint.x > bottomRight.x || mousePoint.y < topLeft.y
                || mousePoint.y > bottomRight.y) {
            return Double.MAX_VALUE;
        }

        double dx = mousePoint.x - center.x;
        double dy = mousePoint.y - center.y;

        double u = ((mousePoint.x - center.x) * dx + (mousePoint.y - center.y) * dy) / (dx * dx + dy * dy);

        Point closestPoint;
        if (u < 0) {
            closestPoint = new Point(center.x, center.y);
        } else if (u > 1) {
            closestPoint = new Point(center.x, center.y);
        } else {
            closestPoint = new Point((int) (center.x + u * dx), (int) (center.y + u * dy));
        }

        // System.out.println("Oval closest distance: " + closestPoint.distance(mousePoint));
        return closestPoint.distance(mousePoint);
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval();
    }

    @Override
    public String getShapeID() {
        return "@OVAL";
    }

    @Override
    public void save(List<String> rows) {
        Point hp0 = getHotPoint(0);
        Point hp1 = getHotPoint(1);
        rows.add(getShapeID() + " " + hp0.x + " " + hp0.y + " " + hp1.x + " " + hp1.y);
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] parts = data.split(" ");
        int x1 = Integer.parseInt(parts[1]);
        int y1 = Integer.parseInt(parts[2]);
        int x2 = Integer.parseInt(parts[3]);
        int y2 = Integer.parseInt(parts[4]);
        Oval oval = new Oval(new Point[] {new Point(x1, y1),  new Point(x2, y2)});
        stack.push(oval);
    }

}
