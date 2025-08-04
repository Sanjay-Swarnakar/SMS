<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Sidebar -->
<div class="sidebar" id="sidebar">
	<h2>Teacher Menu</h2>
	<a href="/SMS/teacher_new.jsp">Dashboard</a>
	<a href="TeacherProfile">My Profile</a>
	<a href="ManageCourses">Manage Courses</a>
	<a href="GradeSubmissions">Grade Submissions</a>
	<a href="AttendanceTracker">Track Attendance</a>
	<a href="Messages">Messages</a>
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
