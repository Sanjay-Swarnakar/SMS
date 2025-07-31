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
@WebServlet("/AddCourse")
public class AddCourse extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        int credits = Integer.parseInt(request.getParameter("credits"));

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO courses(course_name, desciption, credits) VALUES (?, ?, ?)");) {
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setInt(3, credits);
            ps.executeUpdate();
            response.sendRedirect("manage_courses.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Failed to add course");
        }
    }
}