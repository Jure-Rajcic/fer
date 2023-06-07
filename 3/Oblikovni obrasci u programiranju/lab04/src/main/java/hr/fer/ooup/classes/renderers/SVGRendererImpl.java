package hr.fer.ooup.classes.renderers;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.ooup.interfaces.Renderer;

public class SVGRendererImpl implements Renderer {

    private static final String SVG_FOLDER = "data/svg/";
    private static final String EXTENSION = ".svg";

	private final String fileName;
	private final List<String> lines;

	
	public SVGRendererImpl(String fileName, int width, int height) {
		// zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
		// <svg xmlns=... >
		// ...
        this.fileName = fileName;
		this.lines = new LinkedList<>();
		lines.add(String.format(
			"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\">",
			width, height
		));
    }
	
	
	@Override
	public void drawLine(Point s, Point e, Color c) {
		// Dodaj u lines redak koji definira linijski segment:
		// <line ... />
        String line = String.format(
            "   <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:rgb(%d,%d,%d);stroke-width:2\"   />",
            s.x, s.y, e.x, e.y, c.getRed(), c.getGreen(), c.getBlue()
        );
        lines.add(line);
	}

	@Override
	public void fillPolygon(Point[] points, Color c) {
		// Dodaj u lines redak koji definira popunjeni poligon:
		// <polygon points="..." style="stroke: ...; fill: ...;" />
        String colorHex = Integer.toHexString(c.getRGB());
        String oppositeColorHex = Integer.toHexString(c.getRGB() ^ 0x00ffffff);

        StringBuilder sb = new StringBuilder("<polygon points=\"");
		for(Point p : points) {
			sb.append(p.getX() + "," + p.getY() + " ");
		}
        sb.append("\" style=\"stroke:#" + oppositeColorHex + "; fill:#" + colorHex + ";\"/>");
		lines.add(sb.toString());
	}

    public void close() throws IOException {
		// u lines još dodaj završni tag SVG dokumenta: </svg>
		// sve retke u listi lines zapiši na disk u datoteku
		// ...
        lines.add("</svg>");
        Files.write(Paths.get(SVG_FOLDER + fileName + EXTENSION), lines);
	}

}
