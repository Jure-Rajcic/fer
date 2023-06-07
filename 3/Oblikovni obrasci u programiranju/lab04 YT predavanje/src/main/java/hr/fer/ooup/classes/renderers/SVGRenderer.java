package hr.fer.ooup.classes.renderers;

import java.awt.Color;
import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;

import hr.fer.ooup.interfaces.Renderer;

public class SVGRenderer implements Renderer{

    private Rectangle boundBox = new Rectangle();
    private List<String> lines = new ArrayList<String>();

    @Override
    public void drawLine(int x0, int y0, int x1, int y1, Color color) {
        String line = String.format(
            "   <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(%d,%d,%d);stroke-width:2\" />",
            x0, y0, x1, y1, color.getRed(), color.getGreen(), color.getBlue()
        );
        lines.add(line);

        boundBox = boundBox.union(
            new Rectangle(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x0 - x1), Math.abs(y0 - y1))
        );

    }

    @Override
    public void drawLineDashed(int x0, int y0, int x1, int y1, Color color, float[] pattern) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pattern.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(pattern[i]);
        }

        String line = String.format(
            "   <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(%d,%d,%d);stroke-width:2;stroke-dasharray:%s\" />",
            x0, y0, x1, y1, color.getRed(), color.getGreen(), color.getBlue(), sb.toString()
        );

        lines.add(line);

        boundBox = boundBox.union(
            new Rectangle(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x0 - x1), Math.abs(y0 - y1))
        );
    }

    public String getSVG() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"));
        sb.append(String.format("<svg width=\"%d\" height=\"%d\">\n", boundBox.width + 1, boundBox.height + 1));
        for (String line : lines) {
            sb.append(line);
            sb.append("\n");
        }
        sb.append("</svg>\n");
        return sb.toString();
    }
    
}
