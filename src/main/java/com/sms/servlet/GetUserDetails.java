package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.json.JSONObject;

@WebServlet("/GetUserDetails")
public class GetUserDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        response.setContentType("application/json");
        JSONObject json = new JSONObject();

        if (username != null && !username.isEmpty()) {
            try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT fname, lname, email FROM users WHERE username = ?")) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        json.put("fname", rs.getString("fname"));
                        json.put("lname", rs.getString("lname"));
                        json.put("email", rs.getString("email"));
                    }
                }
            } catch (SQLException e) {
                json.put("error", "Database error");
            }
        }

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
