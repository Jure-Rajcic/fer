<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
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
            <c:forEach var="vote" items="${votes}">
                <tr>
                    <td>${vote.getName()}</td>
                    <td>${vote.getVotes()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Grafički prikaz rezultata</h2>
    <img alt="Pie-chart" src="<c:url value='/glasanje-grafika'/>" width="500" height="400" />

    <h2>Rezultati u XLS formatu</h2>
    <p>Rezultati u XLS formatu dostupni su <a href="${pageContext.request.contextPath}/glasanje-xls">ovdje</a></p>

    <h2>Razno</h2>
    <p>Primjeri pjesama pobjedničkih bendova:</p>
    <ul>
        <c:forEach var="winner" items="${winners}">
            <li><a href="${winner.getLink()}">${winner.getName()}</a></li>
        </c:forEach>
    </ul>

</body>
</html>
