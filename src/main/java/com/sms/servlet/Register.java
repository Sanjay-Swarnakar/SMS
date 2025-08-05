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

@WebServlet("/Register")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Collect form data
        String fname = request.getParameter("fname");
        String mname = request.getParameter("mname");
        String lname = request.getParameter("lname");
		String fullname = fname + " " + mname + " " + lname;
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String addr = request.getParameter("addr");
        String phone = request.getParameter("phone");
        String crn = request.getParameter("crn");
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        String pwd1 = request.getParameter("pwd1");
        String hashedPassword = BCrypt.hashpw(pwd, BCrypt.gensalt());

        if (dob == null || dob.trim().isEmpty()) {
            out.println("<script>alert('Date of birth is required!'); history.back();</script>");
            return;
        }

        if (!pwd.equals(pwd1)) {
            out.println("<script type='text/javascript'>");
            out.println("alert('Passwords do not match!');");
            out.println("history.back();"); // Sends the user back to the registration form
            out.println("</script>");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            // Check if user already exists
            String checkUserSQL = "SELECT COUNT(*) FROM users WHERE username = ?";
            String checkApplication = "SELECT COUNT(*) FROM new_acc_req WHERE username = ?";
            
            try (PreparedStatement checkStmt = con.prepareStatement(checkApplication)) {
                checkStmt.setString(1, uname);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // If Application exists, send an error message
                        request.setAttribute("errorMessage", "Application already submitted for the username");
                        RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
            }
            try (PreparedStatement checkStmt = con.prepareStatement(checkUserSQL)) {
                checkStmt.setString(1, uname);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // If user exists, send an error message
                        request.setAttribute("errorMessage", "User already exists. Please choose another username");
                        RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
            }
            
            // Insert into new_acc_req table
            String sql2 = "INSERT INTO new_acc_req (username, fullname, dob, email, address, phone, crn, password) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
                ps2.setString(1, uname);
                ps2.setString(2, fullname);
                ps2.setString(3, dob);
                ps2.setString(4, email);
                ps2.setString(5, addr);
                ps2.setString(6, phone);
                ps2.setString(7, crn);
                ps2.setString(8, hashedPassword);
                int rowsAffected = ps2.executeUpdate();
                
                if (rowsAffected > 0) {
                    // User successfully registered, forward to a success page or show a message
                    request.setAttribute("message", "Application submitted successfully!");
                    RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
                    rd.forward(request, response);
                } else {
                    // Something went wrong with the insertion
                    request.setAttribute("errorMessage", "Error submitting application. Please try again.");
                    RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
                    rd.forward(request, response);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "Error occurred: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            rd.forward(request, response);
        }
    }
}
