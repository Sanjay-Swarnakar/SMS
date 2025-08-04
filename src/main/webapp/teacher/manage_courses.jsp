<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn" %>


<%@ include file="/auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<title>Manage Courses</title>
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/global.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/teacher.css">
		<style>
			/* Simple table styling */
			table {
				border-collapse: collapse;
				width: 100%;
				margin-top: 20px;
			}
			th, td {
				padding: 10px;
				border: 1px solid #ddd;
				text-align: left;
			}
			th {
				background-color: #27ae60;
				color: white;
			}
			input[type="text"] {
				width: 100%;
				padding: 5px;
			}
			button {
				margin-top: 15px;
				padding: 10px 20px;
				background-color: #27ae60;
				border: none;
				color: white;
				cursor: pointer;
			}
			button:hover {
				background-color: #219150;
			}
		</style>
	</head>
	<body>
		<%@ include file="teacher_sidebar.jsp" %>
		<div class="container">
			<h2>Manage Your Courses & Schedules</h2>
			<form method="post" action="ManageCourses">
				<table>
					<thead>
						<tr>
							<th>Course Name</th>
							<th>Schedule (Edit)</th>
						</tr>
					</thead>
					<tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td>
                                <input type="hidden" name="courseId" value="${course.courseId}" />
                                ${course.courseName}
                            </td>
                            <td>
                                <input type="text" name="schedule" value="${course.schedule}" />
                            </td>
                        </tr>
                    </c:forEach>
					</tbody>
				</table>
				<button type="submit">Update Schedules</button>
			</form>
		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>
