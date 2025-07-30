package com.sms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import com.sms.util.DBConnection;

@WebServlet("/GetUsers")
public class GetUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        ArrayList<String> userList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT username FROM users where username != 'admin'");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                userList.add(rs.getString("username"));
            }

            request.setAttribute("userList", userList);
            request.getRequestDispatcher("getUsers.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occured");
        }
    }
}
