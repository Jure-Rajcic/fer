<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String message = (String) request.getAttribute("message");
    String nick = (String) request.getAttribute("nick");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Login Form</title>
  <style>
    .container {
      width: 300px;
      padding: 16px;
      background-color: #f2f2f2;
      margin: 0 auto;
      margin-top: 100px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 12px 20px;
      margin: 8px 0;
      display: inline-block;
      border: 1px solid #ccc;
      box-sizing: border-box;
    }
    input[type="submit"] {
      background-color: #4CAF50;
      color: white;
      padding: 14px 20px;
      margin: 8px 0;
      border: none;
      cursor: pointer;
      width: 100%;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
  </style>
  <script>
    window.onload = function() {
      <% if (message != null) { %>
        alert("<%= message %>");
      <% } %>
      
      <% if (nick != null) { %>
        document.getElementById("nick").value = "<%= nick %>";
      <% } %>
    }
  </script>
</head>
<body>
  <div class="container">
    <form method="post" action="/blog/servleti/main">
      <label for="nick">Nickname:</label>
      <input type="text" id="nick" name="nick" required><br>

      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required><br>

      <input type="submit" value="Log In">
    </form>

    <a href="/blog/servleti/register">Register</a>
    <br>
    <a href="/blog/servleti/author/">Authors</a>
    <br>
    <a href="/blog/servleti/logout">Logout</a>
    
  </div>
</body>
</html>
