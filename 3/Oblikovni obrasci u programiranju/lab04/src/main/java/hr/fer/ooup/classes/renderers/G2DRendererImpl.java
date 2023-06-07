package hr.fer.ooup.classes.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.ooup.interfaces.Renderer;

public class G2DRendererImpl implements Renderer {

	private final Graphics2D g2d;
    private final Color originalColor;
	
	public G2DRendererImpl(Graphics2D g2d) {
        this.g2d = g2d;
        this.originalColor = g2d.getColor();
	}


    @Override
    public void drawLine(Point s, Point e, Color c) {
        g2d.setColor(c);
        g2d.drawLine(s.x, s.y, e.x, e.y);
        g2d.setColor(originalColor);
    }

    @Override
    public void fillPolygon(Point[] points, Color c) {
        g2d.setColor(c);

        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; ++i) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }
        g2d.fillPolygon(xPoints, yPoints, points.length);

        g2d.setColor(originalColor);
    }

}
