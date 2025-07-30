<%@ page isErrorPage="true" %>
<html>
<head><title>Error</title></head>
<body>
    <h2>Error Occurred</h2>
    <p><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "Unknown error." %></p>
</body>
</html>
