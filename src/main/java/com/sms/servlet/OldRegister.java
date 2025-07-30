/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.sms.servlet;

import com.sms.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Sanjay Swarnakar
 */

public class OldRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String fname = request.getParameter("fname");
        String mname = request.getParameter("mname");
        String lname = request.getParameter("lname");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String addr = request.getParameter("addr");
        String phone = request.getParameter("phone");
        String crn = request.getParameter("crn");
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        String pwd1 = request.getParameter("pwd1");
        
        if (!pwd.equals(pwd1)) {
            out.println("<h3 style='color:red'>Passwords do not match!</h3>");
            return;
        }
        
        try (Connection con = DBConnection.getConnection()) {
            String sql1 = "INSERT INTO users (username, password) " +
                         "VALUES (?, ?,)";
            String sql2 = "INSERT INTO users (fname, mname, lname, dob, email, address, phone, crn, username) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
            try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
                ps2.setString(1, fname);
                ps2.setString(2, mname);
                ps2.setString(3, lname);
                ps2.setString(4, dob);
                ps2.setString(5, email);
                ps2.setString(6, addr);
                ps2.setString(7, phone);
                ps2.setString(8, crn);
                ps2.setString(9, uname);
                ps2.setString(10, pwd);

                int i1 = ps2.executeUpdate();

                if (i1 > 0) {
                    out.println("<h3 style='color:green'>Registration successful!</h3>");
                } else {
                    out.println("<h3 style='color:red'>Failed to register.</h3>");
                }
            }
        } catch (Exception e) {
            out.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        } finally {
            out.close();
        }
    }
}
