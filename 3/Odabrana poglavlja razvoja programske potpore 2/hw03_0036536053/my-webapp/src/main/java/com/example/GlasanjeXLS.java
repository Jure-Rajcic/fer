package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@WebServlet("/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {
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

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Voting Results");

        // Header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Band");
        headerRow.createCell(1).setCellValue("Number of Votes");

        // Data rows
        int rowIndex = 1;
        for (Vote vote : votes.values()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(vote.getName());
            row.createCell(1).setCellValue(vote.getVotes());
        }

        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"voting_results.xls\"");

        try (OutputStream outputStream = resp.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
