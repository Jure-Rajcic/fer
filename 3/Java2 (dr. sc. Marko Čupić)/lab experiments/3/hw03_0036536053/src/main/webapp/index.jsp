<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Color Chooser</title>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
    <p><a href="colors.jsp">Background color chooser</a></p>
    <p><a href="trigonometric?a=0&b=90">Trigonometric Table (0-90)</a></p>
    <form action="trigonometric" method="GET">
        Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
        Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
        <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
    </form>
    <p><a href="stories/funny.jsp">Funny Story</a></p>
    <a href="report.jsp">OS Usage Report</a>
    <p><a href="powers?a=1&b=100&n=3">Generate Excel file (a=1, b=100, n=3)</a></p>
    <p><a href="appinfo.jsp">View Web Application Running Time</a></p>
</body>
</html>
