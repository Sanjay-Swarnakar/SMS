<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Sidebar -->
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
