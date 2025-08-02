package sms.student.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Course;

@WebServlet(name = "ViewCourses", urlPatterns = {"/Student/ViewCourses"})
public class ViewCourses extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = Integer.parseInt((String) session.getAttribute("id"));

        List<Course> courses = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = """
                    SELECT c.id, c.name, c.description, c.credits
                    FROM courses c
                    JOIN enrollments e ON c.id = e.course_id
                    WHERE e.student_id = ?
                """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                int credits = rs.getInt("credits");

                courses.add(new Course(id, name, desc, credits));
            }

        } catch (SQLException e) {
            throw new ServletException("Error fetching courses", e);
        }

        request.setAttribute("enrolledCourses", courses);
        request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
    }
}
