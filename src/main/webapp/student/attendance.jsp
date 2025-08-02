<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.AttendanceRecord"%>
<%
    List<AttendanceRecord> records = (List<AttendanceRecord>) request.getAttribute("attendanceList");
    Double overallPercentage = (Double) request.getAttribute("overallPercentage");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Attendance</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/student.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/grades.css">
</head>
<body>
    <jsp:include page="student_nav.jsp" />

    <div class="container">
        <h2>My Attendance</h2>

        <% if (records == null || records.isEmpty()) { %>
            <p>No attendance records found.</p>
        <% } else { %>
            <p><strong>Overall Attendance:</strong> <%= String.format("%.2f", overallPercentage) %> %</p>

            <table class="table">
                <thead>
                    <tr>
                        <th>Subject</th>
                        <th>Classes Attended</th>
                        <th>Total Classes</th>
                        <th>Percentage</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (AttendanceRecord ar : records) { %>
                        <tr>
                            <td><%= ar.getSubject() %></td>
                            <td><%= ar.getAttended() %></td>
                            <td><%= ar.getTotal() %></td>
                            <td><%= String.format("%.2f", ar.getPercentage()) %> %</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
