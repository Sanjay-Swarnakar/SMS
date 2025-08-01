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
import java.sql.SQLException;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet(name = "UpdateSettings", urlPatterns = {"/UpdateSettings"})
public class UpdateSettings extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String academicYear = request.getParameter("academicYear");
        String holidays = request.getParameter("holidays");
        String defaultRole = request.getParameter("defaultRole");

        try (Connection conn = DBConnection.getConnection()) {
            updateSetting(conn, "academic_year", academicYear);
            updateSetting(conn, "holidays", holidays);
            updateSetting(conn, "default_role", defaultRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageSettings");
    }

    private void updateSetting(Connection conn, String key, String value) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE settings SET setting_value = ? WHERE setting_key = ?")) {
            stmt.setString(1, value);
            stmt.setString(2, key);
            stmt.executeUpdate();
        }
    }
}