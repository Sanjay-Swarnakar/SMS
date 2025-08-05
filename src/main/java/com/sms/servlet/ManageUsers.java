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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(ManageUsers.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userQuery = "SELECT u.id, u.username, role, email, phone, address, fname, mname, lname " +
						   "FROM users u JOIN users_detail ud ON u.id = ud.user_id;";
		String roleQuery = "SELECT setting_value FROM settings WHERE setting_key = 'default_role';";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(userQuery);
			 PreparedStatement ps1 = con.prepareStatement(roleQuery);
			 ResultSet rs = ps.executeQuery();
			 ResultSet rs1 = ps1.executeQuery()) {

			List<User> userList = new ArrayList<>();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));

				String mname = rs.getString("mname");
				if (mname == null || mname.trim().isEmpty()) {
					mname = "";
				}
				user.setFullname(rs.getString("fname") + " " + mname + " " + rs.getString("lname"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setRole(rs.getString("role"));

				userList.add(user);
			}

			if (rs1.next()) {
				request.setAttribute("default_role", rs1.getString("setting_value"));
			}

			request.setAttribute("userList", userList);
			request.getRequestDispatcher("manage_users.jsp").forward(request, response);

		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Database error while loading users", ex);
		}
	}
}
