package hr.fer.oprpp2.p08;

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

import hr.fer.oprpp2.p08.dao.provider.PollOptionsServiceProvider;
import hr.fer.oprpp2.p08.model.PollOptions;
import hr.fer.oprpp2.p08.services.PollOptionsServiceI;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("GlasanjeGrafika doGet()");
        PollOptionsServiceI pollOptionsService = PollOptionsServiceProvider.getPollOptionsService();
        List<PollOptions> pollOptions = pollOptionsService.getAllPollOptions();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (PollOptions po : pollOptions) {
            dataset.setValue(po.getOptionTitle(), po.getVotesCount());
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
