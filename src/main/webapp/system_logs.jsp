<%@page import="model.SystemLog"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<% List<SystemLog> logs = (List<SystemLog>) request.getAttribute("logs"); %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>System Logs</title>
		<link rel="stylesheet" href="css/global.css">
		<link rel="stylesheet" href="css/manage_users.css">
	</head>
	<body>
		<%@ include file="admin_menu.jsp" %>

		<div class="container">
			<h3>View Login History, Errors, and Events</h3>
			<table>
				<tr>
					<th>ID</th>
					<th>Type</th>
					<th>User</th>
					<th>Message</th>
					<th>Timestamp</th>
				</tr>
				<%
					if (logs != null && !logs.isEmpty()) {
						for (SystemLog log : logs) {
				%>
				<tr>
					<td><%= log.getId()%></td>
					<td><%= log.getLogType()%></td>
					<td><%= log.getUser()%></td>
					<td><%= log.getMessage()%></td>
					<td><%= log.getTimestamp()%></td>
				</tr>
				<%
					}
				} else {
				%>
				<tr><td colspan="5">No system logs available.</td></tr>
				<%
					}
				%>
			</table>
		</div>

		<%@ include file="footer.jsp" %>
	</body>
</html>
