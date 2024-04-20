package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("text/html");

        context.write("<html><body><table border='1'>");

        for (String paramName : context.getParameterNames()) {
            String paramValue = context.getParameter(paramName);
            context.write("<tr><td>" + paramName + "</td><td>" + paramValue + "</td></tr>");
        }

        context.write("</table></body></html>");
    }
}
