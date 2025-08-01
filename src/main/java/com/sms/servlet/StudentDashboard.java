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
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/StudentDashboard")
public class StudentDashboard extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(StudentDashboard.class.getName());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String id = (session != null) ? (String) session.getAttribute("id") : null;

		if (id == null || id.isEmpty()) {
			response.sendRedirect("login.jsp");
			return;
		}

		try (Connection conn = DBConnection.getConnection()) {

			// --- Load Student Info ---
			PreparedStatement psStudent = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
			psStudent.setString(1, id);
			ResultSet rsStudent = psStudent.executeQuery();

			Map<String, String> studentInfo = new HashMap<>();
			if (rsStudent.next()) {
				studentInfo.put("fname", rsStudent.getString("fname"));
				studentInfo.put("mname", rsStudent.getString("mname"));
				studentInfo.put("lname", rsStudent.getString("lname"));
				studentInfo.put("email", rsStudent.getString("email"));
				studentInfo.put("phone", rsStudent.getString("phone"));
			}

			// --- Load Courses and Grades ---
			PreparedStatement psCourses = conn.prepareStatement(
				"SELECT c.course_name, c.credits, g.grade " +
				"FROM Enrollments e " +
				"JOIN Courses c ON e.course_id = c.course_id " +
				"LEFT JOIN Grades g ON e.enrollment_id = g.enrollment_id " +
				"WHERE e.student_id = ?"
			);
			psCourses.setString(1, id);
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
			
			// --- Send Data to JSP ---
			LOGGER.info(() -> "Student Dashboard - Session attributes: username=" + 
					session.getAttribute("username") + ", student_id=" + session.getAttribute(id));

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
		doPost(request, response);
	}
}
