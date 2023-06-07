package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

    private static final String img1 = "/images/sibenik.jpeg";
    private static final String img2 = "/images/jeju.jpeg";

    @Override
    public void processRequest(RequestContext context) {

        if (context.getParameter("a") == null ) context.setTemporaryParameter("a", "1");
        else context.setTemporaryParameter("a", context.getParameter("a"));

        if (context.getParameter("b") == null ) context.setTemporaryParameter("b", "2");
        else context.setTemporaryParameter("b", context.getParameter("b"));

        int sum = Integer.parseInt(context.getTemporaryParameter("a")) + Integer.parseInt(context.getTemporaryParameter("b"));
        context.setTemporaryParameter("zbroj", String.valueOf(sum));

        String imgName = sum % 2 == 0 ? img1 : img2;
        context.setTemporaryParameter("imgName", imgName);
        try {
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
}
