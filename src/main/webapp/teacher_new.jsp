<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Teacher Dashboard</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="css/global.css">
		<link rel="stylesheet" href="css/teacher.css">
	</head>
	<body>
		<div class="sidebar" id="sidebar">
			<h2>Teacher Menu</h2>
			<a href="Teacher/TeacherProfile">My Profile</a>
			<a href="Teacher/ManageCourses">Manage Courses</a>
			<a href="Teacher/GradeSubmissions">Grade Submissions</a>
			<a href="Teacher/AttendanceTracker">Track Attendance</a>
			<a href="Teacher/Messages">Messages</a>
			<a href="logout.jsp">Logout</a>
		</div>

		<div class="main">
			<header>
				<span class="title">Welcome, <%= session.getAttribute("username")%></span>
				<div>
					<button class="dark-mode-toggle" onclick="toggleDarkMode()">🌓 Dark Mode</button>
					<span class="toggle-btn" onclick="toggleSidebar()">☰</span>
				</div>
			</header>

			<div class="dashboard-container">
				<div class="card">
					<h3>Profile</h3>
					<p>Update your teaching profile and contact info.</p>
					<a href="Teacher/TeacherProfile">View Profile</a>
				</div>

				<div class="card">
					<h3>Courses</h3>
					<p>Manage your assigned courses and schedules.</p>
					<a href="Teacher/ManageCourses">Manage Courses</a>
				</div>

				<div class="card">
					<h3>Submissions</h3>
					<p>View and grade student assignments and exams.</p>
					<a href="Teacher/GradeSubmissions">Grade Submissions</a>
				</div>

				<div class="card">
					<h3>Attendance</h3>
					<p>Record and review student attendance records.</p>
					<a href="Teacher/AttendanceTracker">Track Attendance</a>
				</div>

				<div class="card">
					<h3>Messages</h3>
					<p>Communicate with students and parents.</p>
					<a href="Teacher/Messages">View Messages</a>
				</div>

				<div class="card">
					<h3>Logout</h3>
					<p>End your session securely.</p>
					<a href="logout.jsp">Logout</a>
				</div>
			</div>
		</div>

		<!-- Overlay for mobile sidebar -->
		<div id="overlay" onclick="toggleSidebar()" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:#00000077;z-index:999;"></div>

		<script>
			// same toggle functions as in student.jsp
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
