package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse and validate the input parameters
        int a = request.getParameter("a") == null ? 0 : Integer.parseInt(request.getParameter("a"));
        int b = request.getParameter("b") == null ? 360 : Integer.parseInt(request.getParameter("b"));

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        if (b > a + 720) {
            b = a + 720;
        }

        // Store the values in the request object
        request.setAttribute("a", a);
        request.setAttribute("b", b);

        // Forward to the JSP page for rendering
        request.getRequestDispatcher("trigonometric.jsp").forward(request, response);
    }
}
