package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;

@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Map<String, Vote> votes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vote vote = new Vote(line);
                votes.put(vote.getId(), vote);
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(Vote.SEPARATOR);
                System.out.println(parts[0] + " " + parts[1]);
                votes.get(parts[0]).setVotes(Integer.parseInt(parts[1]));
            }
        }

        // Find the winning bands
        List<Vote> winners = new ArrayList<>();
        int maxVotes = 0;
        for (Vote vote : votes.values()) {
            if (vote.getVotes() > maxVotes) {
                winners.clear();
                winners.add(vote);
                maxVotes = vote.getVotes();
            } else if (vote.getVotes() == maxVotes) {
                winners.add(vote);
            }
        }

        req.setAttribute("votes", votes.values());
        req.setAttribute("winners", winners);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
