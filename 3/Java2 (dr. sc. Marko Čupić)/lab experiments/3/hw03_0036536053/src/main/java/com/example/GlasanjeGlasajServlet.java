package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;

@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandId = req.getParameter("id");
        Map<String, Integer> votes = new HashMap<>();

        

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(Vote.SEPARATOR);
                votes.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        votes.put(bandId, votes.getOrDefault(bandId, 0) + 1);

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                pw.println(entry.getKey() + Vote.SEPARATOR + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
