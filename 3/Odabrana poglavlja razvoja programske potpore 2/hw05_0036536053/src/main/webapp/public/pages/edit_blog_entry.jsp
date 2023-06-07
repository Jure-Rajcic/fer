<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String nick = (String) request.getAttribute("nick");
    Long blogEntryId = (Long) request.getAttribute("blogEntryId");
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
        .button-container {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            width: 500px;
            margin: auto;
            padding: 20px;
        }
        
        .button-container form {
            display: flex;
            flex-direction: column;
        }
        
        .button-container label {
            margin-bottom: 10px;
            font-weight: bold;
        }
        
        .button-container input[type="text"] {
            border: 1px solid #ddd;
            border-radius: 5px;
            height: 35px;
            padding: 5px 10px;
            font-size: 16px;
            margin-bottom: 20px;
        }
        
        .button-container input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            border: none;
            border-radius: 5px;
            height: 35px;
            cursor: pointer;
            font-size: 16px;
        }
        
        .button-container input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .button-container textarea {
            border: 1px solid #ddd;
            border-radius: 5px;
            height: 100px; /* You can adjust the height to your preference */
            padding: 5px 10px;
            font-size: 16px;
            margin-bottom: 20px;
            resize: vertical; /* Allows users to resize the textarea vertically */
        }
        
        
    </style>
</head>
<body>
    <div class="button-container">
        <form method="post" action="<%=request.getContextPath()%>/servleti/edit_blog_entry">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title"><br>

            <label for="text">Text:</label>
            <textarea id="text" name="text"></textarea><br>

            <input type="hidden" name="nick" value=<%=nick%>>
            <input type="hidden" name="blogEntryId" value=<%=blogEntryId%>>
            
            <input type="submit" value="Update Entity">

        </form>
    </div>
</body>
</html>
