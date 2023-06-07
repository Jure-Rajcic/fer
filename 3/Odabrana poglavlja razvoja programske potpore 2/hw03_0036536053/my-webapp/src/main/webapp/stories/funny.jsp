<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Funny Story</title>
    <%
        String[] colors = {"red", "blue", "green", "purple", "orange", "pink"};
        int randomIndex = (int) (Math.random() * colors.length);
        String randomColor = colors[randomIndex];
    %>
    <style>
        .story-text {
            color: <%= randomColor %>;
        }
        .hidden-answer {
            display: none;
        }
    </style>
    <script>
        function toggleAnswer() {
            var answerElement = document.getElementById("hidden-answer");
            if (answerElement.style.display === "none") {
                answerElement.style.display = "block";
            } else {
                answerElement.style.display = "none";
            }
        }
    </script>
</head>
<body style="background-color: <%= session.getAttribute("pickedBgCol") == null ? "white" : session.getAttribute("pickedBgCol") %>;">
    <div class="story-text">
        <h2>A Funny Story</h2>
        <p>
           What is brown and sticky?
        </p>
        <p id="hidden-answer" class="hidden-answer">
            A stick.
        </p>
        <button onclick="toggleAnswer()">Show/Hide Answer</button>
    </div>
</body>
</html>
