<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.util.List"%>

<%
    List<BlogEntry> entries = (List<BlogEntry>) request.getAttribute("entries");
    String nick = (String) request.getAttribute("nick");
    boolean authorLoggedIn = (boolean) request.getAttribute("authorLoggedIn");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Author Blog Entries</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f2f2f2;
        }
        h1 {
            color: #333;
        }
        ul {
            list-style-type: none;
        }
        li {
            border-bottom: 1px solid #ccc;
            padding: 15px;
            color: #333;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .title {
            font-weight: bold;
            color: #007BFF;
        }
        .created, .modified {
            font-size: 0.8em;
            color: #666;
        }
        .add-button, .entry-edit-button {
            display: inline-block;
            background-color: #007BFF;
            color: #000;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            text-decoration: none;
            font-family: 'Roboto', sans-serif;
            cursor: pointer;
            font-size: 16px;
        }
        .add-button:hover, .entry-edit-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div>
        <h1>Blog entries by <%= nick %> </h1>
        <hr>
        <ul>
        <% for (BlogEntry be : entries) { %>
            <li>
                <div>
                    <span class="title"> <a href="<%=request.getContextPath()%>/servleti/author/<%=nick%>/<%=be.getId()%>"> <%=be.getTitle()%> </a> </span><br>
                    Created at: <span class="created"><%=be.getCreatedAt()%></span><br>
                    Last modified at: <span class="modified"><%=be.getLastModifiedAt()%></span><br>
                </div>
                <% if (authorLoggedIn) { %>
                    <form method="get" action="<%=request.getContextPath()%>/servleti/edit_blog_entry">
                        <input type="hidden" name="nick" value="<%=nick%>">
                        <input type="hidden" name="blogEntryId" value="<%=be.getId()%>">
                        <input type="submit" class="entry-edit-button" value="Edit">
                    </form>
                <% } %>
            </li>
        <% } %>
        </ul>
        <% if (authorLoggedIn) { %>
            <div style="display: flex; justify-content: center; align-items: center;">
                <a class="add-button" href="<%=request.getContextPath()%>/servleti/new_blog_entry">Add New Entity</a>
            </div>
        <% } %>
    </div>
</body>
</html>
