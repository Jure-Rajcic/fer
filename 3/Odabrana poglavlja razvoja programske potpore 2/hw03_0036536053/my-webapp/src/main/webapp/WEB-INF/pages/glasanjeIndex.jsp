<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Glasanje</title>
</head>
<body>
    <h1>Glasanje za omiljeni bend:</h1>
    <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
    <ol>
        <c:forEach var="band" items="${bands}">
            <c:set var="bandData" value="${fn:split(band, ',')}" />
            <li><a href="glasanje-glasaj?id=${bandData[0]}">${bandData[1]}</a></li>
        </c:forEach>
    </ol>
</body>
</html>
