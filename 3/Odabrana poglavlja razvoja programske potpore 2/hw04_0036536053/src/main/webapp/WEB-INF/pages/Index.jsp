<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.oprpp2.p08.model.Polls"%>
<%@page import="java.util.List"%>
<%
  List<Polls> polls = (List<Polls>)request.getAttribute("polls");
%>
<html>
  <body>
  <b>Pronađeni su sljedeće ankete:</b><br>

  <% if(polls.isEmpty()) { %>
    Nema anketa.
  <% } else { %>
    <ul>
    <% for(Polls p : polls) { %>
      <a href="/webapp-baza/servleti/glasanje?pollID=<%=p.getId()%>"><%= p.getTitle() %></a>
    <% } %>  
    </ul>
  <% } %>
  </body>
</html>