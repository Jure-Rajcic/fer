package hr.fer.oprpp2.p08;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.p08.dao.provider.PollOptionsServiceProvider;
import hr.fer.oprpp2.p08.dao.provider.PollServiceProvider;
import hr.fer.oprpp2.p08.model.PollOptions;
import hr.fer.oprpp2.p08.model.Polls;


@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("VoteServlet.doGet()");
        Integer pollID = Integer.parseInt(req.getParameter("pollID"));
        
        List<PollOptions> bands = PollOptionsServiceProvider.getPollOptionsService().getPoolOptionsForId(pollID);
        req.setAttribute("bands", bands);

        Polls poll = PollServiceProvider.getPollService().getPoolForId(pollID);
        req.setAttribute("poll", poll);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}


