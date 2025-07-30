<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Student Dashboard</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			:root {
				--bg: #f4f6f8;
				--text: #333;
				--card: #fff;
				--primary: #2e86de;
				--header-bg: #2e86de;
				--header-text: white;
			}

			html {
				transition: background-color 0.4s ease, color 0.4s ease;
			}

			body.dark-mode {
				--bg: #1e1e1e;
				--text: #ddd;
				--card: #2c2c2c;
				--primary: #5dade2;
				--header-bg: #121212;
				--header-text: #ddd;
			}

			* {
				box-sizing: border-box;
				margin: 0;
				padding: 0;
			}

			body {
				font-family: 'Segoe UI', sans-serif;
				background-color: var(--bg);
				color: var(--text);
				display: flex;
				min-height: 100vh;
			}

			.sidebar {
				width: 220px;
				background: var(--card);
				padding: 20px;
				border-right: 1px solid #ccc;
				transition: transform 0.3s ease;
			}

			.sidebar h2 {
				margin-bottom: 20px;
				color: var(--primary);
			}

			.sidebar a {
				display: block;
				margin: 10px 0;
				color: var(--text);
				text-decoration: none;
			}

			.sidebar a:hover {
				color: var(--primary);
			}

			.main {
				flex: 1;
				display: flex;
				flex-direction: column;
			}

			header {
				background-color: var(--header-bg);
				color: var(--header-text);
				padding: 15px 20px;
				display: flex;
				align-items: center;
				justify-content: space-between;
			}

			header .title {
				font-size: 1.4em;
			}

			.dashboard-container {
				display: grid;
				grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
				gap: 20px;
				padding: 20px;
			}

			.card {
				background-color: var(--card);
				padding: 20px;
				border-radius: 12px;
				box-shadow: 0 2px 8px rgba(0,0,0,0.1);
				transition: transform 0.2s;
			}

			.card:hover {
				transform: translateY(-5px);
			}

			.card h3 {
				margin-bottom: 10px;
				color: var(--primary);
			}

			.notifications {
				position: relative;
			}

			.notifications-panel {
				position: absolute;
				top: 30px;
				right: 0;
				background: var(--card);
				color: var(--text);
				box-shadow: 0 2px 10px rgba(0,0,0,0.2);
				border-radius: 8px;
				width: 200px;
				display: none;
				z-index: 100;
			}

			.notifications-panel ul {
				list-style: none;
				padding: 10px;
			}

			.notifications-panel ul li {
				padding: 8px;
				border-bottom: 1px solid #ccc;
			}

			.toggle-btn {
				cursor: pointer;
				margin-left: 15px;
			}

			.dark-mode-toggle {
				background: none;
				color: var(--header-text);
				border: 1px solid #aaa;
				padding: 5px 10px;
				border-radius: 6px;
				cursor: pointer;
			}

			.sidebar {
				width: 220px;
				background: var(--card);
				padding: 20px;
				border-right: 1px solid #ccc;
				transition: transform 0.3s ease;
			}

			.sidebar.collapsed {
				transform: translateX(-100%);
				position: absolute;
				left: 0;
				top: 0;
				z-index: 1000;
				height: 100%;
			}

		</style>
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
					<a href="profile.jsp">Go to Profile</a>
				</div>

				<div class="card">
					<h3>Courses</h3>
					<p>View your enrolled courses and credits.</p>
					<a href="courses.jsp">View Courses</a>
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