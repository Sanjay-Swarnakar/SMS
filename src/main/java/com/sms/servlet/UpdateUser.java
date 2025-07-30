package com.sms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

import com.sms.util.DBConnection;

@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String fname = request.getParameter("fname");
        String mname = request.getParameter("mname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String addr = request.getParameter("addr");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE users SET fname=?, mname=?, lname=?, email=?, addr=?, phone=?, role=? WHERE username=?")) {

            ps.setString(1, fname);
            ps.setString(2, mname);
            ps.setString(3, lname);
            ps.setString(4, email);
            ps.setString(5, addr);
            ps.setString(6, phone);
            ps.setString(7, role);
            ps.setString(8, username);

            int rowsUpdated = ps.executeUpdate();
            response.sendRedirect("edit_user.jsp?username=" + username + "&updated=" + rowsUpdated);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}
