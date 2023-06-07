package hr.fer.ooup.classes.shape;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Stack;

import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.Renderer;

public class LineSegment extends AbstractGraphicalObject {

    private static final Color LINE_SEGMENT_COLOR = Color.ORANGE;
    public LineSegment() {
        super(new Point[] { new Point(0, 0), new Point(10, 0) });
    }

    public LineSegment(Point[] hotPoints) {
        super(hotPoints);
    }

    @Override
    public void render(Renderer r) {

        Point p1 = getHotPoint(0);
        Point p2 = getHotPoint(1);

        r.drawLine(p1, p2, LINE_SEGMENT_COLOR);
    }


    @Override
    public Rectangle getBoundingBox() {
        Point p1 = getHotPoint(0);
        Point p2 = getHotPoint(1);

        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);

        return new Rectangle(x, y, width, height);
    }

    

    @Override
    public double selectionDistance(Point mousePoint) {
        Point p1 = getHotPoint(0);
        Point p2 = getHotPoint(1);

        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;

        double u = ((mousePoint.x - p1.x) * dx + (mousePoint.y - p1.y) * dy) / (dx * dx + dy * dy);

        Point closestPoint;
        if (u < 0) {
            closestPoint = p1;
        } else if (u > 1) {
            closestPoint = p2;
        } else {
            closestPoint = new Point((int) (p1.x + u * dx), (int) (p1.y + u * dy));
        }

        return closestPoint.distance(mousePoint);
    }

    @Override
    public String getShapeName() {
        return "Line";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment();
    }

    

   @Override
   public String getShapeID() {
         return "@LINE";
   }

   @Override
   public void save(List<String> rows) {
        Point start = getHotPoint(0);
        Point end = getHotPoint(1);
        rows.add(getShapeID() + " " + start.x + " " + start.y + " " + end.x + " " + end.y );
   }

   @Override
   public void load(Stack<GraphicalObject> stack, String data) {
        String[] parts = data.split(" ");
        int x1 = Integer.parseInt(parts[1]);
        int y1 = Integer.parseInt(parts[2]);
        int x2 = Integer.parseInt(parts[3]);
        int y2 = Integer.parseInt(parts[4]);
        LineSegment lineSegment = new LineSegment(new Point[] { new Point(x1, y1), new Point(x2, y2) });
        stack.push(lineSegment);
   }

    
    
}
