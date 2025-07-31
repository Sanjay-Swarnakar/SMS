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

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Login1.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection con = DBConnection.getConnection(); 
				PreparedStatement ps = con.prepareStatement("SELECT ud.id, ud.username, role, email, phone, address, fname, mname, lname FROM users_detail ud join users u on ud.username=u.username")) {
			try (ResultSet rs = ps.executeQuery()) {

				List<User> userList = new ArrayList<>();

				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					String mname = rs.getString("mname");
					if (mname == null || mname.trim().isEmpty()) {
						mname = ""; // or any default value you want, like "N/A"
					}
					user.setFullname(rs.getString("fname") + " " + mname + " " + rs.getString("lname"));
					user.setEmail(rs.getString("email"));
					user.setPhone(rs.getString("phone"));
					user.setAddress(rs.getString("address"));
					user.setRole(rs.getString("role"));
					userList.add(user);
				}

				request.setAttribute("userList", userList);
				request.getRequestDispatcher("manage_users.jsp").forward(request, response);
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Database error", ex);
			System.getLogger(ManageUsers.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
		}
	}
}
