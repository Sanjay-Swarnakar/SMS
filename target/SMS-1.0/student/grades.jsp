<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Grade"%>
<%
	List<Grade> grades = (List<Grade>) request.getAttribute("grades");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>My Grades</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/student.css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/grades.css">

	</head>
	<body>
		<%--<jsp:include page="student_nav.jsp" />--%>
		<div class="sidebar" id="sidebar">
			<h2>Student Panel</h2>
			<a href="student.jsp">Dashboard</a>
			<a href="profile.jsp">My Profile</a>
			<a href="courses.jsp">My Courses</a>
			<a href="grades.jsp">My Grades</a>
			<a href="attendance.jsp">Attendance</a>
			<a href="assignments.jsp">Assignments</a>
			<a href="logout.jsp">Logout</a>
		</div>

		<!-- Main Content -->
		<div class="main">
			<header>
				<span class="title">Welcome, <%= session.getAttribute("username")%></span>
				<div>
					<button class="dark-mode-toggle" onclick="toggleDarkMode()">🌓 Dark Mode</button>
					<span class="toggle-btn" onclick="toggleSidebar()">☰</span>
				</div>
			</header>

			<div class="container">
				<h2>My Grades</h2>

				<% if (grades == null || grades.isEmpty()) { %>
				<p>No grades available yet.</p>
				<% } else { %>
				<table class="table">
					<thead>
						<tr>
							<th>Subject</th>
							<th>Grade</th>
							<th>Remarks</th>
						</tr>
					</thead>
					<tbody>
						<% for (Grade g : grades) {%>
                        <tr>
                            <td><%= g.getSubject()%></td>
                            <td><%= g.getGrade()%></td>
                            <td><%= g.getRemarks()%></td>
                        </tr>
						<% } %>
					</tbody>
				</table>
				<% }%>
			</div>

			<%--<jsp:include page="footer.jsp" />--%>
			<!-- Footer -->
			<footer class="footer">
				<p>&copy; <%= java.time.Year.now()%> Student Management System. All rights reserved.</p>
			</footer>
		</div>
		<!-- Dark Mode + Sidebar Toggle Scripts -->
		<div id="overlay" onclick="toggleSidebar()" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:#00000077;z-index:999;"></div>

		<script>
			document.addEventListener('DOMContentLoaded', function () {
				let savedTheme = localStorage.getItem('theme');
				if (!savedTheme) {
					savedTheme = window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
					localStorage.setItem('theme', savedTheme);
				}
				if (savedTheme === 'dark')
					document.body.classList.add('dark-mode');
			});

			function toggleDarkMode() {
				const isDark = document.body.classList.toggle('dark-mode');
				localStorage.setItem('theme', isDark ? 'dark' : 'light');
			}

			function toggleSidebar() {
				const sidebar = document.getElementById('sidebar');
				const overlay = document.getElementById('overlay');
				const isCollapsed = sidebar.classList.toggle('collapsed');
				if (window.innerWidth <= 768) {
					overlay.style.display = isCollapsed ? 'none' : 'block';
				}
			}
		</script>
	</body>
</html>
