<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>
<%@ include file="auth.jsp" %>
<%	HttpSession sess = request.getSession(false);
	if (sess == null || !"admin".equalsIgnoreCase((String) sess.getAttribute("role"))) {
		out.println("<h3>Access Denied: You do not have permission to view this page.</h3>");
		return;
	}

	// Get dashboard attributes from servlet
	Object totalStudents = request.getAttribute("totalStudents");
	Object totalTeachers = request.getAttribute("totalTeachers");
	Object scheduledExams = request.getAttribute("scheduledExams");

	// Format pending fees
	String formattedFees = "0";
	Object feeAttr = request.getAttribute("pendingFees");
	if (feeAttr != null) {
		double fee = Double.parseDouble(feeAttr.toString());
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		formattedFees = formatter.format(fee).replace("₹", "Rs.");
	}

	// Optional alert messages
	String errorMessage = (String) request.getAttribute("errorMessage");
	String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Admin Dashboard</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		<link rel="stylesheet" href="css/admin.css">
	</head>
	<body>

		<header>Student Management System</header>

		<nav>
			<a href="registration.jsp">Register</a>
			<a href="fees.jsp">Fees</a>
			<a href="exam.jsp">Exam</a>
			<a href="logout.jsp">Logout</a>
		</nav>

		<div class="dashboard">
			<h2>Admin Dashboard</h2>

			<!-- Dashboard Cards -->
			<div class="cards">
				<div class="card">
					<img src="/SMS/images/student.png" alt="Total Students">
					<h3>Total Students</h3>
					<p><%= totalStudents != null ? totalStudents : "N/A"%></p>
				</div>
				<div class="card">
					<img src="/SMS/images/teacher.png" alt="Total Teachers">
					<h3>Total Teachers</h3>
					<p><%= totalTeachers != null ? totalTeachers : "N/A"%></p>
				</div>
				<div class="card">
					<img src="/SMS/images/fees.jpeg" alt="Pending Fees">
					<h3>Pending Fees</h3>
					<p><%= formattedFees%></p>
				</div>
				<div class="card">
					<img src="/SMS/images/exam.webp" alt="Scheduled Exams">
					<h3>Exams Scheduled</h3>
					<p><%= scheduledExams != null ? scheduledExams : "0"%></p>
				</div>
			</div>

			<!-- Chart -->
			<div class="chart-placeholder">
				<h3>Performance Overview</h3>
				<canvas id="performanceChart"></canvas>
			</div>

			<script>
				const ctx = document.getElementById('performanceChart').getContext('2d');
				const performanceChart = new Chart(ctx, {
					type: 'bar',
					data: {
						labels: ['Math', 'Science', 'English', 'History', 'Art'],
						datasets: [{
								label: 'Average Scores',
								data: [75, 88, 92, 70, 85],
								backgroundColor: 'rgba(153, 51, 102, 0.7)',
								borderColor: 'rgba(153, 51, 102, 1)',
								borderWidth: 1
							}]
					},
					options: {
						responsive: true,
						scales: {
							y: {
								beginAtZero: true,
								max: 100
							}
						}
					}
				});
			</script>

			<!-- Add User Form -->
			<div class="user-form">
				<h3>Add New User</h3>
				<form method="post" action="AddUserOld">
					<input type="text" name="username" placeholder="Username" required>
					<input type="text" name="role" placeholder="Role" required>
					<input type="password" name="password" placeholder="Password" required>
					<button type="submit">Add User</button>
				</form>
			</div>

			<!-- Update User Section -->
			<div class="cards">
				<div class="card">
					<h3>Update User Details</h3>
					<form method="get" action="EditUserPageServlet">
						<button type="submit">Update User</button>
					</form>
				</div>
			</div>
		</div>

		<!-- Alerts -->
		<% if (errorMessage != null) {%>
		<script>
			alert("<%= errorMessage%>");
		</script>
		<% } %>

		<% if (message != null) {%>
		<script>
			alert("<%= message%>");
		</script>
		<% }%>

	</body>
</html>
