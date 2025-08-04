/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
@WebServlet(name = "ApproveUser", urlPatterns = {"/ApproveUser"})
public class ApproveUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try (Connection conn = DBConnection.getConnection()) {
			// Copy to users table
			String copy = "INSERT INTO users_detail (username, fname, email, phone, address) SELECT username, fullname, email, phone, address FROM new_acc_req WHERE id=?";
			String copy2 = "INSERT INTO users (username, password, role) SELECT username, password, role FROM new_acc_req where id=?";
			
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(copy2);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(copy);
			ps.setInt(1, id);
			ps.executeUpdate();

			// Delete from request table
			String delete = "DELETE FROM new_acc_req WHERE id=?";
			ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			ps.executeUpdate();
			conn.commit();
			
			response.sendRedirect("NewAccountRequests");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Error approving user");
		}
	}
}
