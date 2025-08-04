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
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String username = request.getParameter("username");

		if (idParam == null || idParam.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user ID");
			return;
		}

		int id = Integer.parseInt(idParam);

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id=?")) {

			ps.setString(1, username);
			int affected = ps.executeUpdate();

			if (affected > 0) {
				response.sendRedirect("ManageUsers");
			} else {
				response.getWriter().write("User not found or could not delete.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user");
		}
	}
}
