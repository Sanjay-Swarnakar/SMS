<%@page import="model.User"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>New Account Requests</title>
		<link rel="stylesheet" href="css/global.css">
		<link rel="stylesheet" href="css/manage_users.css">
		<!--<link rel="stylesheet" href="css/table.css">-->
	</head>
	<body>
		<%@ include file="admin_menu.jsp" %>

		<div class="container">
			<h2>New Account Requests</h2>
			<table>
				<tr>
					<th>Username</th>
					<th>Full Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Address</th>
					<th>Role</th>
					<th>Actions</th>
				</tr>
				<%				List<User> requests = (List<User>) request.getAttribute("requestList");
					if (requests != null && !requests.isEmpty()) {
						for (User req : requests) {
				%>
				<tr>
					<td><%= req.getUsername()%></td>
					<td><%= req.getFullname()%></td>
					<td><%= req.getEmail()%></td>
					<td><%= req.getPhone()%></td>
					<td><%= req.getAddress()%></td>
					<td><%= req.getRole()%></td>
					<td>
						<form method="post" action="ApproveUser" style="display:inline;">
							<input type="hidden" name="id" value="<%= req.getId()%>">
							<button class="btn">✅ Approve</button>
						</form>
						<form method="post" action="RejectUser" style="display:inline;">
							<input type="hidden" name="id" value="<%= req.getId()%>">
							<button class="btn">❌ Reject</button>
						</form>
					</td>
				</tr>
				<%
					}
				} else {
				%>
				<tr><td colspan="7">No new account requests.</td></tr>
				<%
					}
				%>
			</table>
		</div>

		<%@ include file="footer.jsp" %>
	</body>
</html>
