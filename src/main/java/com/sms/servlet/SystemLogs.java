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
import model.SystemLog;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet(name = "SystemLogs", urlPatterns = {"/SystemLogs"})
public class SystemLogs extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<SystemLog> logs = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection()) {
			String sql = "SELECT * FROM system_logs ORDER BY timestamp DESC";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				logs.add(new SystemLog(
						rs.getInt("id"),
						rs.getString("log_type"),
						rs.getString("message"),
						rs.getString("user"),
						rs.getTimestamp("timestamp")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("logs", logs);
		request.getRequestDispatcher("system_logs.jsp").forward(request, response);
	}
}
