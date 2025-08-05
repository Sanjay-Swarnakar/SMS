package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/AddUser")
@MultipartConfig
public class AddUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname").trim();
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String role = request.getParameter("role");
		String hashedPassword = BCrypt.hashpw("user123", BCrypt.gensalt());

		String fname = null, mname = null, lname = null;
		String[] parts = fullname.split("\\s+");
		switch (parts.length) {
			case 2 -> {
				fname = parts[0];
				lname = parts[1];
			}
			case 3 -> {
				fname = parts[0];
				mname = parts[1];
				lname = parts[2];
			}
			default -> fname = fullname;
		}

		try (Connection con = DBConnection.getConnection()) {
			con.setAutoCommit(false); // Start transaction

			// Check if username already exists
			try (PreparedStatement checkUser = con.prepareStatement("SELECT 1 FROM users WHERE username = ?")) {
				checkUser.setString(1, username);
				try (ResultSet rs = checkUser.executeQuery()) {
					if (rs.next()) {
						response.setStatus(HttpServletResponse.SC_CONFLICT);
						response.getWriter().write("Username already exists");
						return;
					}
				}
			}

			// Insert into users
			try (PreparedStatement psUser = con.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
				psUser.setString(1, username);
				psUser.setString(2, hashedPassword);
				psUser.setString(3, role);
				psUser.executeUpdate();
			}

			// Get the new user_id
			int user_id = 0;
			try (PreparedStatement psGetId = con.prepareStatement("SELECT id FROM users WHERE username = ?")) {
				psGetId.setString(1, username);
				try (ResultSet rs = psGetId.executeQuery()) {
					if (rs.next()) {
						user_id = rs.getInt("id");
					} else {
						throw new Exception("User ID fetch failed");
					}
				}
			}

			// Insert into users_detail
			try (PreparedStatement psDetail = con.prepareStatement(
					"INSERT INTO users_detail (fname, mname, lname, email, phone, address, username, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
				psDetail.setString(1, fname);
				psDetail.setString(2, mname);
				psDetail.setString(3, lname);
				psDetail.setString(4, email);
				psDetail.setString(5, phone);
				psDetail.setString(6, address);
				psDetail.setString(7, username);
				psDetail.setInt(8, user_id);
				psDetail.executeUpdate();
			}

			con.commit(); // Everything successful

			response.getWriter().write("success");

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("error");
		}
	}
}
