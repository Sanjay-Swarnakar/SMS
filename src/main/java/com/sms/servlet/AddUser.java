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
		
		// Parse full name into fname, mname, lname
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
			default -> {
				fname = fullname; // fallback if parsing fails
			}
		}

		try (
			Connection con = DBConnection.getConnection();
			PreparedStatement psUser = con.prepareStatement("insert into users(username, password, role) values (?,?,?);");
			PreparedStatement psDetail = con.prepareStatement("insert into users_detail (fname, mname, lname, email, phone, address, username) values(?,?,?,?,?,?,?);");
		) {
			con.setAutoCommit(false); // Start transaction

			// Update users table
			psUser.setString(1, username);
			psUser.setString(2, hashedPassword);
			psUser.setString(3, role);
			psUser.executeUpdate();

			// Update users_detail table
			psDetail.setString(1, fname);
			psDetail.setString(2, mname);
			psDetail.setString(3, lname);
			psDetail.setString(4, email);
			psDetail.setString(5, phone);
			psDetail.setString(6, address);
			psDetail.setString(7, username);
			psDetail.executeUpdate();

			con.commit(); // Commit if both succeed
			response.getWriter().write("success");

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("error");
		}
	}
}
