package sms.teacher.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.TeacherCourse;

@WebServlet("/Teacher/ManageCourses")
public class ManageCoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = Integer.parseInt(session.getAttribute("id").toString());
        List<TeacherCourse> courses = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT tc.id, tc.course_id, c.name, tc.schedule " +
                         "FROM teacher_courses tc " +
                         "JOIN courses c ON tc.course_id = c.id " +
                         "WHERE tc.teacher_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TeacherCourse tc = new TeacherCourse();
                tc.setId(rs.getInt("id"));
                tc.setCourseId(rs.getInt("course_id"));
                tc.setCourseName(rs.getString("name"));
                tc.setSchedule(rs.getString("schedule"));
                courses.add(tc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/teacher/manage_courses.jsp").forward(request, response);
    }

    // Handle updating schedules (simplified)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = Integer.parseInt(session.getAttribute("id").toString());
        String[] courseIds = request.getParameterValues("courseId");
        String[] schedules = request.getParameterValues("schedule");

        if (courseIds != null && schedules != null && courseIds.length == schedules.length) {
            try (Connection con = DBConnection.getConnection()) {
                String updateSql = "UPDATE teacher_courses SET schedule=? WHERE teacher_id=? AND course_id=?";
                PreparedStatement ps = con.prepareStatement(updateSql);

                for (int i = 0; i < courseIds.length; i++) {
                    ps.setString(1, schedules[i]);
                    ps.setInt(2, teacherId);
                    ps.setInt(3, Integer.parseInt(courseIds[i]));
                    ps.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("ManageCourses");
    }
}
