package sms.teacher.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Submission;

@WebServlet("/Teacher/GradeSubmissions")
public class GradeSubmissionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = Integer.parseInt(session.getAttribute("id").toString());
        List<Submission> submissions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT s.submission_id, s.assignment_id, a.title AS assignment_title, " +
                         "s.student_id, CONCAT(st.fname, ' ', st.lname) AS student_name, " +
                         "s.submission_date, s.file_path, s.grade, s.feedback " +
                         "FROM submission s " +
                         "JOIN assignment a ON s.assignment_id = a.assignment_id " +
                         "JOIN students st ON s.student_id = st.id " +
                         "JOIN teacher_courses tc ON a.course_id = tc.course_id " +
                         "WHERE tc.teacher_id = ? " +
                         "ORDER BY s.submission_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Submission sub = new Submission();
                sub.setSubmissionId(rs.getInt("submission_id"));
                sub.setAssignmentId(rs.getInt("assignment_id"));
                sub.setAssignmentTitle(rs.getString("assignment_title"));
                sub.setStudentId(rs.getInt("student_id"));
                sub.setStudentName(rs.getString("student_name"));
                sub.setSubmissionDate(rs.getString("submission_date"));
                sub.setFilePath(rs.getString("file_path"));
                sub.setGrade(rs.getString("grade"));
                sub.setFeedback(rs.getString("feedback"));
                submissions.add(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("/teacher/grade_submissions.jsp").forward(request, response);
    }

    // Handles grading submission form POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        String grade = request.getParameter("grade");
        String feedback = request.getParameter("feedback");

        try (Connection con = DBConnection.getConnection()) {
            String updateSql = "UPDATE submission SET grade=?, feedback=? WHERE submission_id=?";
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setString(1, grade);
            ps.setString(2, feedback);
            ps.setInt(3, submissionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("GradeSubmissions");
    }
}
