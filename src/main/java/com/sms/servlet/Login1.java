package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/Login1")
public class Login1 extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Login1.class.getName());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		LOGGER.info("Login1 servlet accessed");

		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Username and Password are required!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT password, role, id FROM users WHERE username=?")) {

			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String hashedPassword = rs.getString("password");
					String role = rs.getString("role");
					String id = rs.getString("id");

					if (BCrypt.checkpw(password, hashedPassword)) {
						HttpSession session = request.getSession();
						session.setAttribute("username", username);
						session.setAttribute("role", role);
						session.setAttribute("id", id);

						switch (role.toLowerCase()) {
							case "admin" ->
								response.sendRedirect("admin_new.jsp");
							case "teacher" ->
								response.sendRedirect("teacher_new.jsp");
							case "student" -> {
								request.getRequestDispatcher("StudentDashboard").forward(request, response);
							}
							case "exam" ->
								response.sendRedirect("exam.jsp");
							case "parent" ->
								response.sendRedirect("parents.jsp");
							case "library" ->
								response.sendRedirect("library.jsp");
							default ->
								response.sendRedirect("welcome.jsp");
						}
					} else {
						request.setAttribute("errorMessage", "Username or Password do not match!");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("errorMessage", "Username or Password do not match!");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Database error", ex);
			request.setAttribute("errorMessage", "Database connection error. Please contact support.");
			request.getRequestDispatcher("db_error.jsp").forward(request, response);
		}
	}
}
