package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet(name = "StudentDashboard", urlPatterns = {"/StudentDashboard"})
public class StudentDashboard extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(Login1.class.getName());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String studentId = (session != null) ? session.getAttribute("id").toString() : null;

		if (studentId == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		try (
				Connection conn = DBConnection.getConnection();) {
			// Load student info
			PreparedStatement psStudent = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?");
			psStudent.setString(1, studentId);
			ResultSet rsStudent = psStudent.executeQuery();

			Map<String, String> studentInfo = new HashMap<>();

			if (rsStudent.next()) {
				studentInfo.put("firstName", rsStudent.getString("first_name"));
				studentInfo.put("lastName", rsStudent.getString("last_name"));
				studentInfo.put("email", rsStudent.getString("email"));
				studentInfo.put("phone", rsStudent.getString("phone"));
			}
			// Load courses and grades
			PreparedStatement psCourses = conn.prepareStatement(
					"SELECT c.course_name, c.credits, g.grade "
					+ "FROM Enrollments e "
					+ "JOIN Courses c ON e.course_id = c.course_id "
					+ "LEFT JOIN Grades g ON e.enrollment_id = g.enrollment_id "
					+ "WHERE e.student_id = ?"
			);
			psCourses.setString(1, studentId);
			ResultSet rsCourses = psCourses.executeQuery();

			List<Map<String, String>> courseList = new ArrayList<>();
			while (rsCourses.next()) {
				Map<String, String> course = new HashMap<>();
				course.put("courseName", rsCourses.getString("course_name"));
				course.put("credits", String.valueOf(rsCourses.getInt("credits")));
				String grade = rsCourses.getString("grade");
				course.put("grade", (grade != null) ? grade : "Pending");
				courseList.add(course);
			}

			// Pass data to JSP
			LOGGER.info(() -> "Session attributes - username: " + session.getAttribute("username") + ", student_id: " + session.getAttribute("id"));
			request.setAttribute("studentInfo", studentInfo);
			request.setAttribute("courses", courseList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("student.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			throw new ServletException("Database error: " + e.getMessage(), e);
		}
	}
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  // or just redirect if GET not allowed
    }
}
