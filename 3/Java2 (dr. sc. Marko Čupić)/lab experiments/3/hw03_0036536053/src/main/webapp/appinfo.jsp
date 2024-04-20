<%@ page import="java.time.Duration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>App Info</title>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
<%
    long startTime = (long) application.getAttribute("startTime");
    long currentTime = System.currentTimeMillis();
    long durationMillis = currentTime - startTime;

    Duration duration = Duration.ofMillis(durationMillis);
    long days = duration.toDays();
    duration = duration.minusDays(days);
    long hours = duration.toHours();
    duration = duration.minusHours(hours);
    long minutes = duration.toMinutes();
    duration = duration.minusMinutes(minutes);
    long seconds = duration.getSeconds();
    long millis = duration.toMillisPart();
%>
<h1>Web Application Running Time:</h1>
<p><%= days %> days <%= hours %> hours <%= minutes %> minutes <%= seconds %> seconds and <%= millis %> milliseconds</p>
</body>
</html>
