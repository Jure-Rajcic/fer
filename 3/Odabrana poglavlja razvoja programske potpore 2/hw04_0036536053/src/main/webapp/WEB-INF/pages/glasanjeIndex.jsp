<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.oprpp2.p08.model.PollOptions"%>
<%@page import="hr.fer.oprpp2.p08.model.Polls"%>
<%@page import="java.util.List"%>
<%
    List<PollOptions> bands = (List<PollOptions>)request.getAttribute("bands");
    Polls poll = (Polls)request.getAttribute("poll");
%>

<html>
<head>
    <title>Glasanje</title>
</head>
<body>
    <h1>Glasanje za omiljeni bend:</h1>
    <p> <%= poll.getMessage() %></p>
        <% for(int i = 0; i < bands.size(); i++) { %>
            <p>
            <%= (i + 1) + ". " %>
            <a href="/webapp-baza/servleti/glasanje-glasaj?id=<%=bands.get(i).getId()%>"><%= bands.get(i).getOptionTitle() %></a>
            </p>
        <% } %> 
</body>
</html>
