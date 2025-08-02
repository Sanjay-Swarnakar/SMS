<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Sidebar -->
<div class="sidebar" id="sidebar">
	<h2>Student Panel</h2>
	<a href="/SMS/student.jsp">Dashboard</a>
	<a href="Profile">My Profile</a>
	<a href="ViewCourses">My Courses</a>
	<a href="ViewGrades">My Grades</a>
	<a href="Attendance">Attendance</a>
	<a href="Assignments">Assignments</a>
	<a href="/SMS/logout.jsp">Logout</a>
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
