package hr.fer.ooup.classes.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Point;
import java.awt.Rectangle;


public class Square extends AbstractGeometricShape{

    private Point topLeft;
    private int side;


    public Square(Point topLeft, int side) {
        this.topLeft = new Point(topLeft);
        this.side = side;
    }

    @Override
    public void draw() {
        Color color = isSelected() ? Color.RED : Color.BLUE;
        renderer.drawLine(topLeft.x, topLeft.y, topLeft.x + side, topLeft.y, color);
        renderer.drawLine(topLeft.x, topLeft.y, topLeft.x, topLeft.y + side, color);
        renderer.drawLine(topLeft.x + side, topLeft.y, topLeft.x + side, topLeft.y + side, color);
        renderer.drawLine(topLeft.x, topLeft.y + side, topLeft.x + side, topLeft.y + side, color);
    }

    @Override
    public void move(int dx, int dy) {
        topLeft.x += dx;
        topLeft.y += dy;
    }

    @Override
    public double distanceFromPoint(Point p) {
        Double distance = Double.MAX_VALUE;
        distance = Math.min(distance, pointToSegmentDistance(topLeft.x, topLeft.y, topLeft.x + side, topLeft.y, p.x, p.y)); // _
        distance = Math.min(distance, pointToSegmentDistance(topLeft.x, topLeft.y, topLeft.x, topLeft.y + side, p.x, p.y)); // |
        distance = Math.min(distance, pointToSegmentDistance(topLeft.x + side, topLeft.y, topLeft.x + side, topLeft.y + side, p.x, p.y)); // -
        distance = Math.min(distance, pointToSegmentDistance(topLeft.x, topLeft.y + side, topLeft.x + side, topLeft.y + side, p.x, p.y));  // |
        return distance;
    }

    @Override
    public Rectangle calculateBoundingBox() {
        return new Rectangle(topLeft.x, topLeft.y, side, side);
    }

    private static double pointToSegmentDistance(double sx, double sy, double ex, double ey, double tx, double ty) {
        double esx = ex - sx;
        double esy = ey - sy;

        double stx = sx - tx;
        double sty = sy - ty;

        double lambda = -(esx * stx + esy * sty) / (esx * esx + esy * esy);
        if (lambda < 0) return pointToPointDistance(sx, sy, tx, ty);
        else if (lambda > 1) return pointToPointDistance(ex, ey, tx, ty);
        else {
            double px = sx +esx * lambda;
            double py = sy +esy * lambda;
            double ppx = px - tx;
            double ppy = py - ty;
            return Math.sqrt(ppx * ppx + ppy * ppy);
        }
    }

    private static double pointToPointDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setSide(int side) {
        this.side = side;
    }

   
    
}
