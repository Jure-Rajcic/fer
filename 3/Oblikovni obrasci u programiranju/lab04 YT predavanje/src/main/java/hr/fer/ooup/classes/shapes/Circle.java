package hr.fer.ooup.classes.shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;



public class Circle extends AbstractGeometricShape {

    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

   

    @Override
    public void move(int dx, int dy) {
        center.x += dx;
        center.y += dy;

    }

    @Override
    public double distanceFromPoint(Point p) {
        int dx = center.x - p.x;
        int dy = center.y - p.y;
        return Math.abs(Math.sqrt(dx * dx + dy * dy) - radius);
    }

    @Override
    public Rectangle calculateBoundingBox() {
        return new Rectangle(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }


    @Override
    public void draw() {
        Color color = isSelected() ? Color.RED : Color.BLUE;
        int x0 = center.x + radius;
        int y0 = center.y;

        for(int i = 0; i <= 180; i++) {
            double angle = 2* Math.PI * i / 180;

            int x1 = (int) (center.x + radius * Math.cos(angle) + 0.5);
            int y1 = (int) (center.y + radius * Math.sin(angle) + 0.5);

            renderer.drawLine(x0, y0, x1, y1, color);
            
            x0 = x1;
            y0 = y1;
        }
    }



    public Point getCenter() {
        return center;
    }



    public void setRadius(int radius) {
        this.radius = radius;
    }
    
}
