package hr.fer.ooup.interfaces;


import java.awt.Point;
import java.awt.Rectangle;


public interface GeometricShape {
    
    public void draw();
    public boolean isSelected();
    public void setSelected(boolean selected);
    public void move(int dx, int dy);
    public double distanceFromPoint(Point p);
    public Rectangle calculateBoundingBox();
}
