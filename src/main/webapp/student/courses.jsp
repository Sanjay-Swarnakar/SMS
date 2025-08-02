<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Course"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%
    session = request.getSession(false);
    if (session == null || session.getAttribute("id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Course> enrolledCourses = (List<Course>) request.getAttribute("enrolledCourses");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enrolled Courses</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
    <script>
        function toggleDarkMode() {
            document.body.classList.toggle("dark-mode");
            localStorage.setItem("darkMode", document.body.classList.contains("dark-mode"));
        }

        window.onload = () => {
            if (localStorage.getItem("darkMode") === "true") {
                document.body.classList.add("dark-mode");
            }
        };
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0; padding: 0;
        }
        .container {
            padding: 20px;
        }
        h2 {
            margin-bottom: 20px;
        }
        .toggle-btn {
            float: right;
            margin-top: -40px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1em;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        body.dark-mode {
            background-color: #121212;
            color: #fff;
        }
        body.dark-mode table {
            background-color: #1e1e1e;
        }
        body.dark-mode th, body.dark-mode td {
            color: #fff;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Enrolled Courses</h2>
        <button class="toggle-btn" onclick="toggleDarkMode()">Toggle Dark Mode</button>
        <table>
            <thead>
                <tr>
                    <th>Course Name</th>
                    <th>Description</th>
                    <th>Credits</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
                        for (Course course : enrolledCourses) {
                %>
                <tr>
                    <td><%= course.getCourseName() %></td>
                    <td><%= course.getDescription() %></td>
                    <td><%= course.getCredits() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3">You are not enrolled in any courses.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
