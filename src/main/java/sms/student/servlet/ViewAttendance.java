package sms.student.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.AttendanceRecord;

@WebServlet(name = "ViewAttendance", urlPatterns = {"/Student/Attendance"})
public class ViewAttendance extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = Integer.parseInt((String) session.getAttribute("id"));

        List<AttendanceRecord> attendanceList = new ArrayList<>();
        double totalAttended = 0;
        double totalClasses = 0;

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT subject, attended_classes, total_classes FROM attendance WHERE student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String subject = rs.getString("subject");
                int attended = rs.getInt("attended_classes");
                int total = rs.getInt("total_classes");

                totalAttended += attended;
                totalClasses += total;

                attendanceList.add(new AttendanceRecord(subject, attended, total));
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load attendance records", e);
        }

        double overallPercentage = totalClasses == 0 ? 0.0 : (totalAttended / totalClasses) * 100;

        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("overallPercentage", overallPercentage);
        request.getRequestDispatcher("/student/attendance.jsp").forward(request, response);
    }
}
