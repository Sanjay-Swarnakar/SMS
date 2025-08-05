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

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet(name = "ApproveUser", urlPatterns = {"/ApproveUser"})
public class ApproveUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String username = null;
		try (Connection conn = DBConnection.getConnection()) {
			// Copy to users table
			String request_data = "SELECT username, password, fullname, dob, email, phone, address, crn FROM new_acc_req WHERE id=?;";
			String user_detail = "INSERT INTO users_detail (fname, mname, lname, dob, username, email, phone, address, crn) values(?,?,?,?,?,?,?,?,?);";
			String users = "INSERT INTO users (username, password, role) SELECT username, password, role FROM new_acc_req where id=?";
			String student = "INSERT INTO students (fname, mname, lname, dob, email, phone, address, password) values(?,?,?,?,?,?,?,?);";

			PreparedStatement ps = conn.prepareStatement(request_data);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				username = rs.getString("username");
				String password = rs.getString("password"),
						fullname = rs.getString("fullname"),
						fname = null, mname = null, lname = null,
						parts[] = fullname.split("\\s+");
				switch (parts.length) {
					case 2 -> {
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
				String dob = rs.getString("dob"),
						email = rs.getString("email"),
						phone = rs.getString("phone"),
						address = rs.getString("address"),
						crn = rs.getString("crn");

				conn.setAutoCommit(false);
				ps = conn.prepareStatement(users);
				ps.setInt(1, id);
				ps.executeUpdate();

				ps = conn.prepareStatement(user_detail);
				ps.setString(1, fname);
				ps.setString(2, mname);
				ps.setString(3, lname);
				ps.setString(4, dob);
				ps.setString(5, username);
				ps.setString(6, email);
				ps.setString(7, phone);
				ps.setString(8, address);
				ps.setString(9, crn);
				ps.executeUpdate();

				ps = conn.prepareStatement(student);
				ps.setString(1, fname);
				ps.setString(2, mname);
				ps.setString(3, lname);
				ps.setString(4, dob);
				ps.setString(5, email);
				ps.setString(6, phone);
				ps.setString(7, address);
				ps.setString(8, password);
				ps.executeUpdate();

				// Delete from request table
				String delete = "DELETE FROM new_acc_req WHERE id=?";
				ps = conn.prepareStatement(delete);
				ps.setInt(1, id);
				ps.executeUpdate();
				conn.commit();
			}
			String get_id = "SELECT id from users where username=?;";
			ps = conn.prepareStatement(get_id);
			ps.setString(1, username);
			ResultSet rs1 = ps.executeQuery();
			int user_id = 0;
			if (rs1.next()) {
				user_id = rs1.getInt("id");
			}
			ps = conn.prepareStatement("UPDATE users_detail SET user_id = ? where username = ?;");
			ps.setInt(1, user_id);
			ps.setString(2, username);
			ps.executeUpdate();
			response.sendRedirect("NewAccountRequests");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Error approving user");
		}
	}
}
