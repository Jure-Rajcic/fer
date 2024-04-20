package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;

public class BgColorWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) {
        String bgcolor = context.getParameter("bgcolor");
        boolean validColor = bgcolor != null && bgcolor.matches("^[0-9A-Fa-f]{6}$");
        if (validColor) {
            context.setPersistentParameter("bgcolor", bgcolor);
        }
        System.out.println("-------bg----------");
        System.out.println("temporary: " + context.temporaryParameters);
        System.out.println("persistent: " + context.persistentParameters);
        System.out.println("parameters: " + context.parameters);
        System.out.println("bgcolor: " + bgcolor);
        System.out.println("-----------------");

        context.setMimeType("text/html");

        try {
            context.write("<!DOCTYPE html><html><body>");
            context.write("<p>Background color " + (validColor ? "updated" : "not updated") + ".</p>");
            context.write("<p><a href='/index2'>Go to /index2.html</a></p>");
            context.write("</body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
