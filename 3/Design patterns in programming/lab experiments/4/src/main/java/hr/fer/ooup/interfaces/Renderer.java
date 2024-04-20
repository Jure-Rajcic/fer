package hr.fer.ooup.interfaces;

import java.awt.Color;
import java.awt.Point;

public interface Renderer {
	public void drawLine(Point s, Point e, Color c);
	public void fillPolygon(Point[] points, Color c);
}