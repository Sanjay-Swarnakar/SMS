package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet("/EditCourse")
@MultipartConfig
public class EditCourse extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM courses WHERE course_id=?")) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				request.setAttribute("course", rs);
				request.getRequestDispatcher("edit_course.jsp").forward(request, response);
			} else {
				response.sendRedirect("manage_courses.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Error loading course");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("courseName");
		String desc = request.getParameter("description");
		int credits = Integer.parseInt(request.getParameter("credits"));

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE courses SET name=?, description=?, credits=? WHERE id=?")) {
			ps.setString(1, name);
			ps.setString(2, desc);
			ps.setInt(3, credits);
			ps.setInt(4, id);
			ps.executeUpdate();
			response.sendRedirect("ManageCourses");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Failed to update course");
		}
	}
}
