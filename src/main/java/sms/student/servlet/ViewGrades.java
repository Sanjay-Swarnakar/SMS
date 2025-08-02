package sms.student.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Grade;

@WebServlet(name = "ViewGrades", urlPatterns = {"/Student/ViewGrades"})
public class ViewGrades extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int studentId = Integer.parseInt((String) session.getAttribute("id"));
        List<Grade> grades = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT c.name, grade, remarks FROM grades g join courses c on g.course_id=c.id WHERE student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String course = rs.getString("c.name");
                String grade = rs.getString("grade");
                String remarks = rs.getString("remarks");
                grades.add(new Grade(course, grade, remarks));
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve grades", e);
        }

        request.setAttribute("grades", grades);
        request.getRequestDispatcher("/student/grades.jsp").forward(request, response);
    }
}
