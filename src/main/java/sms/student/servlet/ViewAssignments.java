package sms.student.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;

@WebServlet(name = "ViewAssignments", urlPatterns = {"/Student/Assignments"})
public class ViewAssignments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = Integer.parseInt((String) session.getAttribute("id"));
        List<Assignment> assignments = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = """
                SELECT a.id, a.title, a.subject, a.file_name, a.due_date,
                       (SELECT COUNT(*) FROM submissions s WHERE s.assignment_id = a.id AND s.student_id = ?) > 0 AS submitted
                FROM assignments a
                ORDER BY a.due_date
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Assignment a = new Assignment(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("subject"),
                    rs.getString("file_name"),
                    rs.getString("due_date"),
                    rs.getBoolean("submitted")
                );
                assignments.add(a);
            }

        } catch (SQLException e) {
            throw new ServletException("Error loading assignments", e);
        }

        request.setAttribute("assignments", assignments);
        request.getRequestDispatcher("/student/assignments.jsp").forward(request, response);
    }
}
