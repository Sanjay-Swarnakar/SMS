<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<title>Track Attendance</title>
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/global.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/teacher.css">
		<script src="<%= request.getContextPath()%>/js/dark-mode-toggle.js"></script>
		<style>
			table {
				width: 100%;
				border-collapse: collapse;
				margin-top: 20px;
			}
			th, td {
				border: 1px solid #ddd;
				padding: 6px;
				text-align: center;
			}
			th {
				background-color: #27ae60;
				color: white;
			}
			select {
				width: 100px;
			}
			.course-select {
				margin: 15px 0;
			}
		</style>
	</head>
	<body>
		<%@ include file="teacher_sidebar.jsp" %>

		<div class="container">
			<div class="top-bar">
				<h2>Track Attendance</h2>
			</div>
			<form method="get" action="AttendanceTracker">
				<label for="courseId">Select Course:</label>
				<select name="courseId" id="courseId" onchange="this.form.submit()" class="course-select">
					<option value="">--Select--</option>
					<c:forEach var="course" items="${courses}">
						<option value="${course.courseId}" <c:if test="${course.courseId == selectedCourseId}">selected</c:if>>
							${course.courseName}
						</option>
					</c:forEach>
				</select>
			</form>

			<c:if test="${not empty sessions}">
				<form method="post" action="AttendanceTracker">
					<input type="hidden" name="courseId" value="${selectedCourseId}" />
					<table>
						<thead>
							<tr>
								<th>Student Name</th>
									<c:forEach var="session" items="${sessions}">
									<th>${session.sessionDate} <br/> (${session.topic})</th>
									</c:forEach>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="student" items="${students}">
								<tr>
									<td>${student.studentName}</td>
									<c:forEach var="session" items="${sessions}">
										<%
											Integer studentId = (Integer) pageContext.findAttribute("student").getClass().getMethod("getStudentId").invoke(pageContext.findAttribute("student"));
											Integer sessionId = (Integer) pageContext.findAttribute("session").getClass().getMethod("getSessionId").invoke(pageContext.findAttribute("session"));
											Map<Integer, List> attendanceMap = (Map<Integer, List>) request.getAttribute("attendanceMap");
											String status = "Absent";
											Integer recordId = null;
											if (attendanceMap != null && attendanceMap.get(sessionId) != null) {
												for (model.AttendanceRecords record : (List<model.AttendanceRecords>) attendanceMap.get(sessionId)) {
													if (record.getStudentId() == studentId) {
														status = record.getStatus();
														recordId = record.getRecordId();
														break;
													}
												}
											}
										%>
										<td>
											<input type="hidden" name="recordId" value="<%= recordId%>"/>
											<select name="status">
												<option value="Present" <%= "Present".equals(status) ? "selected" : ""%>>Present</option>
												<option value="Absent" <%= "Absent".equals(status) ? "selected" : ""%>>Absent</option>
												<option value="Late" <%= "Late".equals(status) ? "selected" : ""%>>Late</option>
											</select>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<button type="submit" style="margin-top:15px; padding:10px 20px;">Save Attendance</button>
				</form>
			</c:if>

			<c:if test="${empty sessions}">
				<p>No attendance sessions found for the selected course.</p>
			</c:if>
		</div>

		<%@ include file="footer.jsp" %>
	</body>
</html>
