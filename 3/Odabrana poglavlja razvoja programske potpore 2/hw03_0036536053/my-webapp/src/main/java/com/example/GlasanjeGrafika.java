package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
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
                votes.get(parts[0]).setVotes(Integer.parseInt(parts[1]));
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Vote vote : votes.values()) {
            dataset.setValue(vote.getName(), vote.getVotes());
        }

        JFreeChart pieChart = ChartFactory.createPieChart3D("Voting Results", dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) pieChart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        resp.setContentType("image/png");
        OutputStream outputStream = resp.getOutputStream();
        BufferedImage chartImage = pieChart.createBufferedImage(400, 400);
        ImageIO.write(chartImage, "png", outputStream);
        outputStream.close();
    }
}
