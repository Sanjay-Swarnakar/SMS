package com.sms.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/AddUser")
@MultipartConfig
public class AddUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Input validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty() || role == null || role.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required!");
            RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
            rd.forward(request, response);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            // Check if user already exists
            String checkUserSQL = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkUserSQL)) {
                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // If user exists, send an error message
                        request.setAttribute("errorMessage", "User already exists. Please choose another username.");
                        RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
            }

            // Insert new user into the database
            String insertSQL = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                ps.setString(1, username);
                ps.setString(2, hashedPassword);  // You should hash the password before saving
                ps.setString(3, role);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    // User successfully registered, forward to a success page or show a message
                    request.setAttribute("message", "User added successfully!");
                    RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
                    rd.forward(request, response);
                } else {
                    // Something went wrong with the insertion
                    request.setAttribute("errorMessage", "Error registering user. Please try again.");
                    RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
                    rd.forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Error occurred: " + ex.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }
}