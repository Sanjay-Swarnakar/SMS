package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/Login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and Password are required!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
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

                    // Compare entered password with hashed password
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        // Password matched
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("role", role);

                        if ("admin".equalsIgnoreCase(role)) {
                            response.sendRedirect("AdminDashboard");
                        } else if ("teacher".equalsIgnoreCase(role)) {
                            response.sendRedirect("teacher.jsp");
                        } else if ("student".equalsIgnoreCase(role)) {
                            session.setAttribute("student_id", id);
							response.sendRedirect("StudentDashboard");
                        } else {
                            response.sendRedirect("welcome.jsp");
                        }
                    } else {
                        // Password mismatch
                        request.setAttribute("errorMessage", "Username or Password do not match!");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                    }
                } else {
                    // No such username
                    request.setAttribute("errorMessage", "Username or Password do not match!");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            out.println("<h3>Error occurred: " + ex.getMessage() + "</h3>");
        } finally {
            out.close();
        }
    }
}
