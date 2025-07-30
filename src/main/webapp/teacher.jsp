<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Teacher Dashboard</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<style>
			body {
				margin: 0;
				font-family: Arial, sans-serif;
				background: url('/SMS/images/wallpaper2you_115591.jpg') no-repeat center center fixed;
				background-size: cover;
				color: #fff;
			}

			header {
				background-color: #993366;
				padding: 20px;
				text-align: center;
				font-size: 32px;
				font-weight: bold;
			}

			nav {
				display: flex;
				justify-content: center;
				gap: 20px;
				background: rgba(0, 0, 0, 0.7);
				padding: 10px 0;
				width: 100%;
				z-index: 999;
			}

			nav a {
				color: white;
				text-decoration: none;
				padding: 0.6rem 1.2rem;
				background-color: #444;
				border-radius: 5px;
				transition: background-color 0.3s;
			}

			nav a:hover {
				background-color: #666;
			}

			.dashboard-title {
				text-align: center;
				margin: 40px 0 20px;
				font-size: 28px;
				background-color: rgba(0,0,0,0.7);
				padding: 10px;
			}

			.dashboard {
				display: flex;
				flex-wrap: wrap;
				justify-content: center;
				gap: 20px;
				padding: 20px;
				text-decoration: none;
			}

			.dashboard a{
				background-color: rgba(255,255,255,0.9);
				color: #000;
				width: 300px;
				padding: 20px;
				border-radius: 8px;
				text-align: center;
				font-size: 18px;
				font-weight: bold;
				text-decoration: none;
				transition: background-color 0.3s;
			}

			@media (max-width: 768px) {
				header {
					font-size: 24px;
				}

				.card {
					width: 90%;
				}

				nav {
					flex-direction: column;
				}
			}
		</style>
	</head>
	<body>
		<header>
			Student Management System
		</header>

		<nav>
			<a href="exam.jsp">Exam</a>
			<a href="logout.jsp">Logout</a>
		</nav>

		<div class="dashboard-title">TEACHER DASHBOARD</div>

		<div class="dashboard">
			<a href="Student_Detail.jsp">
				Total Students
			</a>
			<a href="Upcoming_Exam.jsp">
				Upcoming Exams
			</a>
			<a href="Classes_Detail.jsp">
				Classes
			</a>
			<a href="Pending_Assignments.jsp">
				Pending Assignments
			</a>
			<a href="Recent_Exams.jsp">
				Recent Exams
			</a>
		</div>

	</body>
</html>
