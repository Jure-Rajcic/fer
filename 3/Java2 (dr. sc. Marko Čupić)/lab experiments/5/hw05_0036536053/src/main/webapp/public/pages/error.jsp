<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String message = (String) request.getAttribute("message");
%>


<!DOCTYPE html>
<html>
<head>
  <title>Error</title>
  
</head>
<body>
  <h1><%= message %></h1>
</body>
</html>
