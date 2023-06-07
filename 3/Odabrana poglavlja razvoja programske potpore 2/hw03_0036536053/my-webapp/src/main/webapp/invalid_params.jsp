<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invalid Parameters</title>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
    <h2>Invalid Parameters</h2>
    <p>Please provide valid input parameters for a, b, and n.</p>
</body>
</html>
