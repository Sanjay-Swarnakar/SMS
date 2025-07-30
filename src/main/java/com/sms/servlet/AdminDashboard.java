package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "AdminDashboard", urlPatterns = {"/AdminDashboard"})
public class AdminDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session & Role check
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int totalStudents = 0;
        int totalTeachers = 0;
        int scheduledExams = 0;
        double pendingFees = 0;

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users WHERE role = 'student'");
            if (rs.next()) totalStudents = rs.getInt(1);

            rs = stmt.executeQuery("SELECT COUNT(*) FROM users WHERE role = 'teacher'");
            if (rs.next()) totalTeachers = rs.getInt(1);

            rs = stmt.executeQuery("SELECT SUM(amount) FROM fees WHERE status = 'pending'");
            if (rs.next()) {
                pendingFees = rs.getDouble(1);
                if (rs.wasNull()) pendingFees = 0.0;
            }

            rs = stmt.executeQuery("SELECT COUNT(*) FROM exams WHERE scheduled = true");
            if (rs.next()) scheduledExams = rs.getInt(1);

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalTeachers", totalTeachers);
        request.setAttribute("pendingFees", pendingFees);
        request.setAttribute("scheduledExams", scheduledExams);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}
