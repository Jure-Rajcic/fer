<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>OS Usage Report</title>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
    <img src="reportImage" alt="Pie Chart" />
</body>
</html>
