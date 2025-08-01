<%@page import="model.SystemLog"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<% List<SystemLog> logs = (List<SystemLog>) request.getAttribute("logs"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>System Logs</title>
    <link rel="stylesheet" href="css/manage_users.css">
</head>
<body>
    <header>
        <h2>System Logs</h2>
        <button class="btn" onclick="toggleDarkMode()">🌓</button>
    </header>

    <div class="container">
        <h3>View Login History, Errors, and Events</h3>
        <table>
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>User</th>
                <th>Message</th>
                <th>Timestamp</th>
            </tr>
            <%
                if (logs != null && !logs.isEmpty()) {
                    for (SystemLog log : logs) {
            %>
            <tr>
                <td><%= log.getId() %></td>
                <td><%= log.getLogType() %></td>
                <td><%= log.getUser() %></td>
                <td><%= log.getMessage() %></td>
                <td><%= log.getTimestamp() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr><td colspan="5">No system logs available.</td></tr>
            <%
                }
            %>
        </table>
    </div>

    <script>
        function toggleDarkMode() {
            document.body.classList.toggle('dark-mode');
            localStorage.setItem('theme', document.body.classList.contains('dark-mode') ? 'dark' : 'light');
        }

        document.addEventListener('DOMContentLoaded', function () {
            const savedTheme = localStorage.getItem('theme');
            if (savedTheme === 'dark') {
                document.body.classList.add('dark-mode');
            }
        });
    </script>
</body>
</html>
