<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>View Reports</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="css/global.css">
		<link rel="stylesheet" href="css/manage_users.css">
		<link rel="stylesheet" href="css/view_reports.css">
	</head>
	<body>
		<%@ include file="admin_menu.jsp" %>

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

		<%@ include file="footer.jsp" %>
	</body>
</html>
