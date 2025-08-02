<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Assignment" %>
<%
    List<Assignment> assignments = (List<Assignment>) request.getAttribute("assignments");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assignments</title>
    <link rel="stylesheet" href="css/student.css"><link rel="stylesheet" href="<%= request.getContextPath() %>/css/student.css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/grades.css">
</head>
<body>
    <jsp:include page="student_nav.jsp" />

    <div class="container">
        <h2>My Assignments</h2>

        <% if (assignments == null || assignments.isEmpty()) { %>
            <p>No assignments available.</p>
        <% } else { %>
            <table class="table">
                <thead>
                    <tr>
                        <th>Subject</th>
                        <th>Title</th>
                        <th>Due Date</th>
                        <th>Status</th>
                        <th>Download</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Assignment a : assignments) { %>
                        <tr>
                            <td><%= a.getSubject() %></td>
                            <td><%= a.getTitle() %></td>
                            <td><%= a.getDueDate() %></td>
                            <td>
                                <% if (a.isSubmitted()) { %>
                                    ✅ Submitted
                                <% } else { %>
                                    ❌ Pending
                                <% } %>
                            </td>
                            <td>
                                <a href="uploads/assignments/<%= a.getFileName() %>" download="<%= a.getFileName() %>">Download</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>
