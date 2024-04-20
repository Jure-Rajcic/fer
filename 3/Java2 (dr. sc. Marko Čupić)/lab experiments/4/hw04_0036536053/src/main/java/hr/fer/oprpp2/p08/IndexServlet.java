package hr.fer.oprpp2.p08;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.p08.dao.provider.PollServiceProvider;
import hr.fer.oprpp2.p08.model.Polls;

@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("IndexServlet.doGet() called.");
        
        List<Polls> polls = PollServiceProvider.getPollService().getAllPools();
        req.setAttribute("polls", polls);

        req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}
	
}


