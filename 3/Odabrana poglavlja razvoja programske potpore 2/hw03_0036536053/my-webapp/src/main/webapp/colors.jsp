<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Colors</title>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
    <a href="setcolor?color=WHITE">WHITE</a><br>
    <a href="setcolor?color=RED">RED</a><br>
    <a href="setcolor?color=GREEN">GREEN</a><br>
    <a href="setcolor?color=CYAN">CYAN</a><br>
</body>
</html>
