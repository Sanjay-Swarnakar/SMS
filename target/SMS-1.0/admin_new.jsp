<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Admin Dashboard</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/global.css">
	<style>
		:root {
			--bg: #f9f9f9;
			--text: #222;
			--card: #ffffff;
			--primary: #c0392b;
			--header-bg: #c0392b;
			--header-text: #ffffff;
		}
		body.dark-mode {
			--bg: #1a1a1a;
			--text: #ddd;
			--card: #2d2d2d;
			--primary: #e74c3c;
			--header-bg: #121212;
			--header-text: #ddd;
		}
		/* Reuse same structure as student.jsp or teacher.jsp */
		html { transition: background 0.4s, color 0.4s; }
	</style>
</head>
<body>
<div class="sidebar" id="sidebar">
	<h2>Admin Menu</h2>
	<a href="manage_users.jsp">Manage Users</a>
	<a href="manage_courses.jsp">Manage Courses</a>
	<a href="site_settings.jsp">Site Settings</a>
	<a href="reports.jsp">Reports</a>
	<a href="logs.jsp">System Logs</a>
	<a href="logout.jsp">Logout</a>
</div>

<div class="main">
	<header>
		<span class="title">Admin Panel – <%= session.getAttribute("username") %></span>
		<div>
			<button class="dark-mode-toggle" onclick="toggleDarkMode()">🌓 Dark Mode</button>
			<span class="toggle-btn" onclick="toggleSidebar()">☰</span>
		</div>
	</header>

	<div class="dashboard-container">
		<div class="card">
			<h3>Users</h3>
			<p>Add/edit/delete students, teachers, and parents.</p>
			<a href="ManageUsers">Manage Users</a>
		</div>

		<div class="card">
			<h3>Courses</h3>
			<p>Create and organize course offerings.</p>
			<a href="ManageCourses">Manage Courses</a>
		</div>

		<div class="card">
			<h3>Site Settings</h3>
			<p>Control academic year, holidays, and defaults.</p>
			<a href="ManageSettings">Settings</a>
		</div>

		<div class="card">
			<h3>Reports</h3>
			<p>Download performance and attendance reports.</p>
			<a href="view_reports.jsp">View Reports</a>
		</div>

		<div class="card">
			<h3>Logs</h3>
			<p>View login history, errors, and system events.</p>
			<a href="logs.jsp">System Logs</a>
		</div>

		<div class="card">
			<h3>Logout</h3>
			<p>Exit the admin panel securely.</p>
			<a href="logout.jsp">Logout</a>
		</div>
	</div>
</div>

<div id="overlay" onclick="toggleSidebar()" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:#00000077;z-index:999;"></div>

<script>
	document.addEventListener('DOMContentLoaded', function () {
		let savedTheme = localStorage.getItem('theme');
		if (!savedTheme) {
			savedTheme = window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
			localStorage.setItem('theme', savedTheme);
		}
		if (savedTheme === 'dark') document.body.classList.add('dark-mode');
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
