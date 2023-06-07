package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String color = request.getParameter("color");
        HttpSession session = request.getSession();
        session.setAttribute("pickedBgCol", color);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

/*
mvn clean jetty:run
http://localhost:8080/webapp2/index.jsp
http://localhost:8080/webapp2/glasanje
*/