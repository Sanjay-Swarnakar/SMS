package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

@WebServlet("/ManageSettings")
public class ManageSettings extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, String> settingsMap = new HashMap<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM settings");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                settingsMap.put(rs.getString("setting_key"), rs.getString("setting_value"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("settings", settingsMap);
        request.getRequestDispatcher("manage_settings.jsp").forward(request, response);
    }
}
