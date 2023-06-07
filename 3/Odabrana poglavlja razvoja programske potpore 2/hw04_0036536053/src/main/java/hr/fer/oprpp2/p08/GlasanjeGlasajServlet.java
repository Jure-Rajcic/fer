package hr.fer.oprpp2.p08;

import javax.servlet.*;
import javax.servlet.http.*;

import hr.fer.oprpp2.p08.dao.provider.PollOptionsServiceProvider;
import hr.fer.oprpp2.p08.services.PollOptionsServiceI;

import javax.servlet.annotation.*;
import java.io.*;

@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Integer bandId = Integer.parseInt(req.getParameter("id"));

        PollOptionsServiceI pollOptionsServiceI = PollOptionsServiceProvider.getPollOptionsService();
        pollOptionsServiceI.incrementVotesForId(bandId);

        resp.sendRedirect( "/webapp-baza/servleti/glasanje-rezultati");
    }
}
