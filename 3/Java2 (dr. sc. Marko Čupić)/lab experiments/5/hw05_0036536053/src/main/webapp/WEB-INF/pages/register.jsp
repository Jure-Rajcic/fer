<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String message = (String) request.getAttribute("message");
    String firstName = (String) request.getAttribute("firstName");
    String lastName = (String) request.getAttribute("lastName");
    String email = (String) request.getAttribute("email");
    String nick = (String) request.getAttribute("nick");

%>

<!DOCTYPE html>
<html>
<head>
  <title>Registration Form</title>
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
    input[type="text"], input[type="email"], input[type="password"] {
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

      <% if (firstName != null) { %>
        document.getElementById("firstName").value = "<%= firstName %>";
      <% } %>

       <% if (lastName != null) { %>
        document.getElementById("lastName").value = "<%= lastName %>";
       <% } %>

       <% if (email != null) { %>
        document.getElementById("email").value = "<%= email %>";
       <% } %>
      
       <% if (nick != null) { %>
        document.getElementById("nick").value = "<%= nick %>";
       <% } %>

    }
  </script>
</head>
<body>
  <div class="container">
    <form method="post" action="/blog/servleti/register">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required><br>

      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required><br>

      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required><br>

      <label for="nick">Nickname:</label>
      <input type="text" id="nick" name="nick" required><br>

      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required><br>

      <input type="submit" value="Register">
    </form>

    <a href="/blog/servleti/main">Login</a>
    <br>
    <a href="/blog/servleti/author/">Authors</a>

  </div>
</body>
</html>
