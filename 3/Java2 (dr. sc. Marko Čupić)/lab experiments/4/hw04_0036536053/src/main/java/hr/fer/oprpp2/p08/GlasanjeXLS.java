package hr.fer.oprpp2.p08;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import hr.fer.oprpp2.p08.dao.provider.PollOptionsServiceProvider;
import hr.fer.oprpp2.p08.model.PollOptions;
import hr.fer.oprpp2.p08.services.PollOptionsServiceI;

@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("GlasanjeXLS doGet()");


        PollOptionsServiceI pollOptionsService = PollOptionsServiceProvider.getPollOptionsService();
        List<PollOptions> pollOptions = pollOptionsService.getAllPollOptions();

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Voting Results");

        // Header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Band");
        headerRow.createCell(1).setCellValue("Number of Votes");

        // Data rows
        int rowIndex = 1;
        for (PollOptions po : pollOptions) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(po.getOptionTitle());
            row.createCell(1).setCellValue(po.getVotesCount());
        }

        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"voting_results.xls\"");

        try (OutputStream outputStream = resp.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
