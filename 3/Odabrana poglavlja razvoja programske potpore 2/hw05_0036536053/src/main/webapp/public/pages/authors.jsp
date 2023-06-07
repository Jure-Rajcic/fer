<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%
    List<BlogUser> authors = (List<BlogUser>) request.getAttribute("authors");
    System.out.println("authors.jsp");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Authors</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f2f2f2;
        }
        h1 {
            color: #333;
        }
        ul {
            list-style-type: none;
        }
        li {
            border-bottom: 1px solid #ccc;
            padding: 15px;
            color: #333;
        }
        a {
            color: #007BFF;
            text-decoration: none;
        }
        a:hover {
            color: #0056b3;
        }
        .nick {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div>
        <h1> Authors in DB </h1>
        <hr>
        <ul>
        <% for (BlogUser bu : authors) { %>
            <li>
                <a href="<%=request.getContextPath()%>/servleti/author/<%=bu.getNick()%>"> 
                    <span class="nick"><%=bu.getNick()%></span><br>
                    Name: <%=bu.getFirstName()%> <%=bu.getLastName()%><br>
                    Email: <%=bu.getEmail()%>
                </a>
            </li>
        <% } %>
        </ul>
    </div>
</body>
</html>
