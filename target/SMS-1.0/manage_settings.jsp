<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<%@ page import="java.util.Map" %>
<%
    Map<String, String> settings = (Map<String, String>) request.getAttribute("settings");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Settings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/manage_users.css">
	<link rel="stylesheet" href="css/manage_settings.css">
</head>
<body>
    <header>
        <h2>Settings</h2>
        <button class="btn" onclick="toggleDarkMode()">🌓</button>
    </header>

    <div class="container">
        <h3>Control academic year, holidays, and default settings</h3>
        <form action="UpdateSettings" method="post" class="settings-form">
            <label>Academic Year:</label>
            <input type="text" name="academicYear" value="<%= settings.get("academic_year") != null ? settings.get("academic_year") : "" %>" required>

            <label>Holidays (comma-separated dates):</label>
            <input type="text" name="holidays" value="<%= settings.get("holidays") != null ? settings.get("holidays") : "" %>">

            <label>Default Role:</label>
            <select name="defaultRole" required>
                <option value="">Select Role</option>
                <option value="admin" <%= "admin".equals(settings.get("default_role")) ? "selected" : "" %>>Admin</option>
                <option value="exam" <%= "exam".equals(settings.get("default_role")) ? "selected" : "" %>>Exam</option>
                <option value="teacher" <%= "teacher".equals(settings.get("default_role")) ? "selected" : "" %>>Teacher</option>
                <option value="student" <%= "student".equals(settings.get("default_role")) ? "selected" : "" %>>Student</option>
                <option value="parent" <%= "parent".equals(settings.get("default_role")) ? "selected" : "" %>>Parent</option>
            </select>

            <button type="submit" class="btn">💾 Save Settings</button>
        </form>
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
