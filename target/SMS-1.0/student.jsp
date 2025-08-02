<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Student Dashboard</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="css/student.css">
	</head>
	<body>

		<div class="sidebar" id="sidebar">
			<h2>Menu</h2>
			<a href="profile.jsp">My Profile</a>
			<a href="courses.jsp">Courses</a>
			<a href="grades.jsp">Grades</a>
			<a href="attendance.jsp">Attendance</a>
			<a href="assignments.jsp">Assignments</a>
			<a href="logout.jsp">Logout</a>
		</div>

		<div class="main">
			<header>
				<span class="title">Welcome, <%= session.getAttribute("username")%></span>
				<div>
					<button class="dark-mode-toggle" onclick="toggleDarkMode()">🌓 Dark Mode</button>
					<span class="toggle-btn" onclick="toggleNotifications()">🔔</span>
					<span class="toggle-btn" onclick="toggleSidebar()">☰</span>

					<div class="notifications-panel" id="notificationsPanel">
						<ul>
							<li>Upcoming exam: Math on Aug 10</li>
							<li>Assignment due: Physics on Aug 5</li>
							<li>75% attendance this semester</li>
						</ul>
					</div>
				</div>
			</header>

			<div class="dashboard-container">
				<div class="card">
					<h3>Profile</h3>
					<p>Update your personal information and password.</p>
					<a href="Student/Profile">Go to Profile</a>
				</div>

				<div class="card">
					<h3>Courses</h3>
					<p>View your enrolled courses and credits.</p>
					<a href="Student/ViewCourses">View Courses</a>
				</div>

				<div class="card">
					<h3>Grades</h3>
					<p>Track academic progress by subject.</p>
					<a href="grades.jsp">View Grades</a>
				</div>

				<div class="card">
					<h3>Attendance</h3>
					<p>Check your attendance percentage and trends.</p>
					<a href="attendance.jsp">View Attendance</a>
				</div>

				<div class="card">
					<h3>Assignments</h3>
					<p>Download pending assignments and track submissions.</p>
					<a href="assignments.jsp">View Assignments</a>
				</div>

				<div class="card">
					<h3>Logout</h3>
					<p>Click below to safely log out.</p>
					<a href="logout.jsp">Logout</a>
				</div>
			</div>
		</div>
		<div id="overlay" onclick="toggleSidebar()" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:#00000077;z-index:999;"></div>

		<script>
			// Detect and apply theme preference on page load
			document.addEventListener('DOMContentLoaded', function () {
				let savedTheme = localStorage.getItem('theme');

				if (!savedTheme) {
					// No saved preference, check system preference
					const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
					savedTheme = prefersDark ? 'dark' : 'light';
					localStorage.setItem('theme', savedTheme);
				}

				if (savedTheme === 'dark') {
					document.body.classList.add('dark-mode');
				}
			});

			// Toggle dark mode and remember it
			function toggleDarkMode() {
				const isDark = document.body.classList.toggle('dark-mode');
				localStorage.setItem('theme', isDark ? 'dark' : 'light');
			}

			function toggleNotifications() {
				const panel = document.getElementById('notificationsPanel');
				panel.style.display = (panel.style.display === 'block') ? 'none' : 'block';
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