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
@WebServlet(name = "RejectUser", urlPatterns = {"/RejectUser"})
public class RejectUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try (Connection conn = DBConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM new_acc_req WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			response.sendRedirect("NewAccountRequests");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500, "Error rejecting user");
		}
	}
}
