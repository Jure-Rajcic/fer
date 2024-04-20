package com.example;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

@WebServlet("/reportImage")
public class PieChartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Linux", 25);
        dataset.setValue("macOS", 25);
        dataset.setValue("Windows", 50);
    
        JFreeChart pieChart = ChartFactory.createPieChart3D("OS Usage Report", dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) pieChart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
    
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BufferedImage chartImage = pieChart.createBufferedImage(600, 400);
        ImageIO.write(chartImage, "png", outputStream);
        outputStream.close();
    }
    
}
