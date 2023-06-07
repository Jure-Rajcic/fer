package com.example;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@WebServlet("/powers")
public class PowersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int a, b, n;
        try {
            a = Integer.parseInt(request.getParameter("a"));
            b = Integer.parseInt(request.getParameter("b"));
            n = Integer.parseInt(request.getParameter("n"));
            if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5 || a > b) {
                request.getRequestDispatcher("/invalid_params.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.getRequestDispatcher("/invalid_params.jsp").forward(request, response);
            return;
        }
        Workbook workbook = new HSSFWorkbook();
        for (int i = 1; i <= n; i++) {
            Sheet sheet = workbook.createSheet("Power " + i);
            for (int j = a; j <= b; j++) {
                Row row = sheet.createRow(j - a);
                row.createCell(0).setCellValue(j);
                row.createCell(1).setCellValue(Math.pow(j, i));
            }
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
