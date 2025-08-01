/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.sms.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet(name = "DownloadReport", urlPatterns = {"/DownloadReport"})
public class DownloadReport extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String type = request.getParameter("type");

        // Define file names and paths based on report type
        String filename;
        String filepath;

        if ("performance".equalsIgnoreCase(type)) {
            filename = "performance_report.pdf";
            filepath = getServletContext().getRealPath("/reports/performance_report.pdf");
        } else if ("attendance".equalsIgnoreCase(type)) {
            filename = "attendance_report.pdf";
            filepath = getServletContext().getRealPath("/reports/attendance_report.pdf");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type.");
            return;
        }

        File file = new File(filepath);

        // Check if file exists
        if (!file.exists()) {
            response.setContentType("text/plain");
            response.getWriter().write("Report not found: " + filename);
            return;
        }

        // Set response headers to force download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength((int) file.length());

        // Stream the file to client
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
             
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}