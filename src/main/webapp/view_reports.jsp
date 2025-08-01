<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>View Reports</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="css/manage_users.css">
		<link rel="stylesheet" href="css/view_reports.css">
	</head>
	<body>
		<header>
			<h2>View Reports</h2>
			<button class="btn" onclick="toggleDarkMode()">🌓</button>
		</header>
		<div class="container">
			<h3>Download Performance and Attendance Reports</h3>

			<div class="report-section">
				<h4>Performance Reports</h4>
				<form action="DownloadReport" method="get">
					<input type="hidden" name="type" value="performance">
					<button class="btn" type="submit">📥 Download Performance Report</button>
				</form>
			</div>

			<div class="report-section">
				<h4>Attendance Reports</h4>
				<form action="DownloadReport" method="get">
					<input type="hidden" name="type" value="attendance">
					<button class="btn" type="submit">📥 Download Attendance Report</button>
				</form>
			</div>
		</div>

		<script>
			function toggleDarkMode() {
				document.body.classList.toggle('dark-mode');
				localStorage.setItem('theme', document.body.classList.contains('dark-mode') ? 'dark' : 'light');
			}

			document.addEventListener('DOMContentLoaded', function () {
				const savedTheme = localStorage.getItem('theme');
				if (savedTheme === 'dark') {
					document.body.classList.add('dark-mode');
				}
			});
		</script>
	</body>
</html>
