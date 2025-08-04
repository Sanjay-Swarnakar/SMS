<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<title>Grade Submissions</title>
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/global.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/teacher.css">
		<style>
			table {
				border-collapse: collapse;
				width: 100%;
				margin-top: 20px;
			}
			th, td {
				border: 1px solid #ddd;
				padding: 8px;
			}
			th {
				background-color: #27ae60;
				color: white;
			}
			input[type=text], textarea {
				width: 100%;
				padding: 6px;
				box-sizing: border-box;
			}
			button {
				background-color: #27ae60;
				color: white;
				border: none;
				padding: 8px 15px;
				cursor: pointer;
			}
			button:hover {
				background-color: #219150;
			}
			.submission-file {
				text-decoration: none;
				color: #2196F3;
			}
		</style>
	</head>
	<body>
		<%@ include file="teacher_sidebar.jsp" %>
		<div class="container">
			<h2>Grade Submissions</h2>
			<table>
				<thead>
					<tr>
						<th>Assignment</th>
						<th>Student</th>
						<th>Submitted On</th>
						<th>File</th>
						<th>Grade</th>
						<th>Feedback</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sub" items="${submissions}">
						<tr>
							<td>${sub.assignmentTitle}</td>
							<td>${sub.studentName}</td>
							<td>${sub.submissionDate}</td>
							<td>
								<c:if test="${not empty sub.filePath}">
									<a href="${pageContext.request.contextPath}/${sub.filePath}" target="_blank" class="submission-file">View</a>
								</c:if>
							</td>
					<form action="GradeSubmissions" method="post">
						<td>
							<input type="hidden" name="submissionId" value="${sub.submissionId}" />
							<input type="text" name="grade" value="${sub.grade}" placeholder="e.g. A, B+, 95" />
						</td>
						<td>
							<textarea name="feedback" rows="2" placeholder="Enter feedback...">${sub.feedback}</textarea>
						</td>
						<td>
							<button type="submit">Save</button>
						</td>
					</form>
                    </tr>
                </c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>
