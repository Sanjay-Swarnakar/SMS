package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import model.User; 

@WebServlet("/NewAccountRequests")
public class NewAccountRequests extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> requests = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection()) {
			String sql = "SELECT * FROM new_acc_req";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User req = new User();
				req.setId(rs.getInt("id"));
				req.setUsername(rs.getString("username"));
				req.setFullname(rs.getString("fullname"));
				req.setEmail(rs.getString("email"));
				req.setPhone(rs.getString("phone"));
				req.setAddress(rs.getString("address"));
				req.setRole(rs.getString("role"));
				requests.add(req);
			}

			request.setAttribute("requestList", requests);
			RequestDispatcher rd = request.getRequestDispatcher("account_requests.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Error loading account requests");
		}
	}
}
