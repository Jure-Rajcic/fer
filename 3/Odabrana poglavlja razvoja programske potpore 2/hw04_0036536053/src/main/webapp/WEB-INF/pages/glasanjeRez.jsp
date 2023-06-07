<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.oprpp2.p08.model.PollOptions"%>
<%@page import="hr.fer.oprpp2.p08.model.Polls"%>
<%@page import="java.util.List"%>

<%
    List<PollOptions> pollOptions = (List<PollOptions>)request.getAttribute("pollOptions");
    List<PollOptions> winners = (List<PollOptions>)request.getAttribute("winners");
    String pieChartUrl = response.encodeURL("/webapp-baza/servleti/glasanje-grafika");
    String xlsUrl = response.encodeURL("/webapp-baza/servleti/glasanje-xls");
%>


<html>
<head>
    <title>Rezultati glasanja</title>
    <style type="text/css">
        table.rez td {text-align: center;}
    </style>
</head>
<body>

    <h1>Rezultati glasanja</h1>
    <p>Ovo su rezultati glasanja.</p>
    <table border="1" cellspacing="0" class="rez">
        <thead>
            <tr>
                <th>Bend</th>
                <th>Broj glasova</th>
            </tr>
        </thead>
        <tbody>
            <% for(int i = 0; i < pollOptions.size(); i++) { %>
                <tr>
                    <td> <%= pollOptions.get(i).getOptionTitle()%> </td>
                    <td> <%= pollOptions.get(i).getVotesCount()%> </td>
                </tr>
            <% } %> 
        </tbody>
    </table>

    <h2>Grafički prikaz rezultata</h2>
    <img alt="Pie-chart" src="<%= pieChartUrl %>" width="500" height="400" />

    <h2>Rezultati u XLS formatu</h2>
    <p>Rezultati u XLS formatu dostupni su <a href=<%= xlsUrl%> >ovdje</a></p>

    <h2>Razno</h2>
    <p>Primjeri pjesama pobjedničkih bendova:</p>
    <ul>
        <% for(PollOptions w: winners) { %>
            <li><a href=<%=w.getOptionLink()%> </a> <%=w.getOptionTitle()%> </li>
        <% } %>      
    </ul>

</body>
</html>
