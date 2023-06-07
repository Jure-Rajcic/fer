package hr.fer.ooup.classes.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

import hr.fer.ooup.interfaces.Renderer;

public class G2DRenderer implements Renderer{

    private Graphics2D g2d;

    public void setG2d(Graphics2D g2d) {
        this.g2d = g2d;
    }   

    @Override
    public void drawLine(int x0, int y0, int x1, int y1, Color color) {
        g2d.setColor(color);
        g2d.drawLine(x0, y0, x1, y1);
    }

    @Override
    public void drawLineDashed(int x0, int y0, int x1, int y1, Color color, float[] pattern) {
        Stroke originalStroke = g2d.getStroke();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(1, java.awt.BasicStroke.CAP_BUTT, java.awt.BasicStroke.JOIN_BEVEL, 1, pattern, 0));
       
        g2d.drawLine(x0, y0, x1, y1);
        
        g2d.setStroke(originalStroke);
    } 
    
}
