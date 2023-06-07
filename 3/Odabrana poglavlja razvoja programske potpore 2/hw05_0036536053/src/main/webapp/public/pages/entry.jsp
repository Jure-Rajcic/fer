<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>

<%
    BlogEntry entry = (BlogEntry) request.getAttribute("entry");
    String nick = (String) request.getAttribute("nick");
    List<BlogComment> comments = entry.getComments();
    boolean authorLoggedIn = (boolean) request.getAttribute("authorLoggedIn");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Author Blog Entry</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
        }

        h1 {
            color: #333;
            margin: 0 0 20px;
        }

        .entry {
            border-bottom: 1px solid #ccc;
            padding: 15px;
            color: #333;
            background-color: #fff;
            margin-bottom: 20px;
        }

        .title {
            font-weight: bold;
            color: #007BFF;
            margin-bottom: 10px;
        }

        .text {
            margin-bottom: 10px;
        }

        .created-at,
        .last-modified-at {
            color: #777;
            font-size: 0.9em;
            margin-bottom: 10px;
        }

        .comments {
            margin-top: 20px;
        }

        .comment {
            border: 1px solid #ccc;
            padding: 10px;
            background-color: #f9f9f9;
            margin-bottom: 10px;
        }

        .comment-author {
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }

        .comment-message {
            color: #333;
        }

        .comment:last-child {
            margin-bottom: 0;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }

        .form-container label {
            margin-bottom: 10px;
            font-weight: bold;
            color: #333;
        }

        .form-container textarea {
            width: 100%;
            height: 100px;
            resize: vertical;
            padding: 5px;
            border: 1px solid #ccc;
        }

        .form-container input[type="submit"] {
            padding: 10px 20px;
            background-color: #007BFF;
            border: none;
            color: #fff;
            cursor: pointer;
            font-weight: bold;
        }

        .form-container input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div>
        <h1> Blog Entry by <%= nick %> </h1>
        <hr>
        <div class="entry">
            <span class="title"><%=entry.getTitle()%></span><br>
            <div class="text"><%=entry.getText()%></div>
            <div class="created-at">Created at: <%=entry.getCreatedAt()%></div>
            <div class="last-modified-at">Last modified at: <%=entry.getLastModifiedAt()%></div>
            <div class="comments">
                <% for (BlogComment comment : comments) { %>
                <div class="comment">
                    <div class="comment-author"><%=comment.getUsersEMail()%></div>
                    <div class="comment-message"><%=comment.getMessage()%></div>
                </div>
                <% } %>
            </div>
        </div>

        <% if (authorLoggedIn) { %>
        <div class="form-container">
            <form method="post" action="<%=request.getContextPath()%>/servleti/new_blog_comment">
                <label for="comment">Comment:</label>
                <textarea id="comment" name="comment" required></textarea><br>
                <input type="submit" value="Post new Comment">

                <input type="hidden" name="entryID" value="<%=entry.getId()%>">
                <input type="hidden" name="nick" value="<%=nick%>">
            </form>
        </div>
        <% } %>
    </div>
</body>
</html>
