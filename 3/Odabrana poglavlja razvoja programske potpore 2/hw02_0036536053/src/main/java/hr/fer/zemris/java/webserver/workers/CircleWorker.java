package hr.fer.zemris.java.webserver.workers;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Base64;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class CircleWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("text/html");

        BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = bim.createGraphics();

        // Set the background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());

        // Draw the filled circle
        g2d.setColor(Color.BLUE);
        int centerX = bim.getWidth() / 2;
        int centerY = bim.getHeight() / 2;
        int radius = 50; // Set the desired radius for the circle

        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        g2d.dispose();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bim, "png", bos);
            String base64Image = Base64.getEncoder().encodeToString(bos.toByteArray());
            context.write("<html><body>");
            context.write("<div style=\"display: flex; justify-content: center; align-items: center; height: 100vh;\">"); // Center the image on the page
            context.write("<img src=\"data:image/png;base64," + base64Image + "\" alt=\"Circle Image\" />");
            context.write("</div>");
            context.write("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
