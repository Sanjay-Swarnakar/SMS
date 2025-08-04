package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet("/ManageCourses")
public class ManageCourses extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Login1.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection conn = DBConnection.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM courses")) {
			try (ResultSet rs = stmt.executeQuery()) {
				List<Course> courseList = new ArrayList<>();
				while (rs.next()) {
					Course course = new Course();
					course.setCourseId(rs.getInt("id"));
					course.setCourseName(rs.getString("name"));
					course.setDescription(rs.getString("description")); // note: column is 'desciption' (typo in db?)
					course.setCredits(rs.getInt("credits"));
					courseList.add(course);
				}
				request.setAttribute("courseList", courseList);
				request.getRequestDispatcher("manage_courses.jsp").forward(request, response);
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Database error", ex);
			System.getLogger(ManageCourses.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
		}
	}
}
