<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="sidebar" id="sidebar">
	<h2>Admin Menu</h2>
	<a href="admin_new.jsp">Dashboard</a>
	<a href="ManageUsers">Manage Users</a>
	<a href="ManageCourses">Manage Courses</a>
	<a href="ManageSettings">Site Settings</a>
	<a href="view_reports.jsp">Reports</a>
	<a href="SystemLogs">System Logs</a>
	<a href="logout.jsp">Logout</a>
</div>

<div class="main">
	<header>
		<span class="title">Welcome – <%= session.getAttribute("username")%></span>
		<div>
			<button class="dark-mode-toggle" onclick="toggleDarkMode()">🌓 Dark Mode</button>
			<span class="toggle-btn" onclick="toggleSidebar()">☰</span>
		</div>
	</header>
