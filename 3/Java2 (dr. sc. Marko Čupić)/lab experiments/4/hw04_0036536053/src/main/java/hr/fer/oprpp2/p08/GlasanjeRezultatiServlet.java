package hr.fer.oprpp2.p08;

import javax.servlet.*;
import javax.servlet.http.*;

import hr.fer.oprpp2.p08.dao.provider.PollOptionsServiceProvider;
import hr.fer.oprpp2.p08.model.PollOptions;
import hr.fer.oprpp2.p08.services.PollOptionsServiceI;

import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;

@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        PollOptionsServiceI pollOptionsServiceI = PollOptionsServiceProvider.getPollOptionsService();
        List<PollOptions> pollOptions = pollOptionsServiceI.getAllPollOptions();
        req.setAttribute("pollOptions", pollOptions);
        
        long maxVotes = pollOptions.stream().mapToLong(PollOptions::getVotesCount).max().getAsLong();
        List<PollOptions> winners = pollOptions.stream().filter(pollOption -> pollOption.getVotesCount() == maxVotes).toList();
        req.setAttribute("winners", winners);
        // winners.stream().forEach(System.out::println);
        
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
