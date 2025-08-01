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

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet("/DeleteCourse")
public class DeleteCourse extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM courses WHERE course_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            response.sendRedirect("ManageCourses");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Failed to delete course");
        }
    }
}
