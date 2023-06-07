<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trigonometric Table</title>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
    <table border="1">
        <tr>
            <th>Degree</th>
            <th>sin(x)</th>
            <th>cos(x)</th>
        </tr>
        <% 
            int a = (Integer) request.getAttribute("a");
            int b = (Integer) request.getAttribute("b");
            for (int i = a; i <= b; i++) {
        %>
        <tr>
            <td><%= i %></td>
            <td><%= Math.sin(Math.toRadians(i)) %></td>
            <td><%= Math.cos(Math.toRadians(i)) %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
