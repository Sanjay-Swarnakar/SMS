package sms.student.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet(name = "UpdateProfile", urlPatterns = {"/Student/UpdateProfile"})
@MultipartConfig
public class UpdateProfile extends HttpServlet {

	private static final String UPLOAD_DIR = "profile_pics";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			int studentId = Integer.parseInt(session.getAttribute("id").toString());
			String name = request.getParameter("name");
			// Parse full name into fname, mname, lname
			String fname = null, mname = null, lname = null;
			String[] parts = name.split("\\s+");
			switch (parts.length) {
				case 2 -> {
					fname = parts[0];
					lname = parts[1];
				}
				case 3 -> {
					fname = parts[0];
					mname = parts[1];
					lname = parts[2];
				}
				default -> {
					fname = name; // fallback if parsing fails
				}
			}
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Part filePart = request.getPart("profilePicture");
			String fileName = null;

			if (filePart != null && filePart.getSize() > 0) {
				fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
				String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				filePart.write(uploadPath + File.separator + fileName);
			}
			try (Connection con = DBConnection.getConnection()) {
				String sql = fileName == null
						? "UPDATE students SET fname=?, mname=?, lname=?, email=?, password=? WHERE id=?"
						: "UPDATE students SET fname=?, mname=?, lname=?, email=?, password=?, profile_picture=? WHERE id=?";

				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, fname);
				ps.setString(2, mname);
				ps.setString(3, lname);
				ps.setString(4, email);
				ps.setString(5, password);

				if (fileName == null) {
					ps.setInt(6, studentId);
				} else {
					ps.setString(6, fileName);
					ps.setInt(7, studentId);
				}

				ps.executeUpdate();
			}
		} catch (ServletException | IOException | NumberFormatException | SQLException e) {
			e.printStackTrace();
			throw new ServletException("Invalid student ID in session", e);
		}

		response.sendRedirect("Profile");
	}
}
