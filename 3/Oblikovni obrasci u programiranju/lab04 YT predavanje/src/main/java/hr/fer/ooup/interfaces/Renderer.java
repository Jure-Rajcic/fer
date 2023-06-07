package hr.fer.ooup.interfaces;

import java.awt.Color;

public interface Renderer {
    public void drawLine(int x0, int y0, int x1, int y1, Color color);
    public void drawLineDashed(int x0, int y0, int x1, int y1, Color color, float[] pattern);
}
