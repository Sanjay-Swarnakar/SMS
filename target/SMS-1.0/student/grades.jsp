<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Grade"%>
<%
	List<Grade> grades = (List<Grade>) request.getAttribute("grades");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>My Grades</title>
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/student.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/grades.css">

	</head>
	<body>
		<jsp:include page="student_nav.jsp" />

		<div class="container">
			<h2>My Grades</h2>

			<% if (grades == null || grades.isEmpty()) { %>
			<p>No grades available yet.</p>
			<% } else { %>
			<table class="table">
				<thead>
					<tr>
						<th>Subject</th>
						<th>Grade</th>
						<th>Remarks</th>
					</tr>
				</thead>
				<tbody>
					<% for (Grade g : grades) {%>
					<tr>
						<td><%= g.getSubject()%></td>
						<td><%= g.getGrade()%></td>
						<td><%= g.getRemarks()%></td>
					</tr>
					<% } %>
				</tbody>
			</table>
			<% }%>
		</div>
		<jsp:include page="footer.jsp" />
	</body>
</html>
