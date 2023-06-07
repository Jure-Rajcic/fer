package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class HomeWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) {
        context.setMimeType("text/html");

        String backgroundColor = context.getParameter("bgcolor") != null
                ? context.getParameter("bgcolor")
                : "7F7F7F";

        System.out.println("---------home--------");
        System.out.println("temporary: " + context.temporaryParameters);
        System.out.println("persistent: " + context.persistentParameters);
        System.out.println("parameters: " + context.parameters);
        System.out.println("bgcolor: " + backgroundColor);
        System.out.println("-----------------");
        context.setTemporaryParameter("background", "#" + backgroundColor);
        try {
            context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
