<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Course"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%
	session = request.getSession(false);
	if (session == null || session.getAttribute("id") == null) {
		response.sendRedirect("login.jsp");
		return;
	}
	List<Course> enrolledCourses = (List<Course>) request.getAttribute("enrolledCourses");
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Enrolled Courses</title>
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/student.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/grades.css">
	</head>
	<body>
		<jsp:include page="student_nav.jsp" />

		<div class="container">
			<h2>Enrolled Courses</h2>

			<table class="table">
				<thead>
					<tr>
						<th>Course Name</th>
						<th>Description</th>
						<th>Credits</th>
					</tr>
				</thead>
				<tbody>
					<%
						if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
							for (Course course : enrolledCourses) {
					%>
					<tr>
						<td><%= course.getCourseName()%></td>
						<td><%= course.getDescription()%></td>
						<td><%= course.getCredits()%></td>
					</tr>
					<%
						}
					} else {
					%>
					<tr>
						<td colspan="3">You are not enrolled in any courses.</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
		<jsp:include page="footer.jsp" />
	</body>
</html>
