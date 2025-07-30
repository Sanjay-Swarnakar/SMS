package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/EditUserPageServlet") // maps to page request
public class EditUserPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> usernames = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT username FROM users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // log error
        }
        request.setAttribute("usernames", usernames);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editUser.jsp");
        dispatcher.forward(request, response);
    }
}
