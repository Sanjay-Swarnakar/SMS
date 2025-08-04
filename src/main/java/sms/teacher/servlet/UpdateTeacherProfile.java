package sms.teacher.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/Teacher/UpdateTeacherProfile")
@MultipartConfig
public class UpdateTeacherProfile extends HttpServlet {
	private static final String UPLOAD_DIR = "profile_pics";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		int teacherId = Integer.parseInt(session.getAttribute("id").toString());
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String contact = request.getParameter("contact");
		String qualification = request.getParameter("qualification");

		Part filePart = request.getPart("profilePicture");
		String fileName = null;

		if (filePart != null && filePart.getSize() > 0) {
			fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
			String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) uploadDir.mkdirs();
			filePart.write(uploadPath + File.separator + fileName);
		}

		try (Connection con = DBConnection.getConnection()) {
			String sql = fileName == null
				? "UPDATE teachers SET fname=?, email=?, password=?, contact=?, qualification=? WHERE id=?"
				: "UPDATE teachers SET fname=?, email=?, password=?, contact=?, qualification=?, profile_picture=? WHERE id=?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, contact);
			ps.setString(5, qualification);

			if (fileName == null) {
				ps.setInt(6, teacherId);
			} else {
				ps.setString(6, fileName);
				ps.setInt(7, teacherId);
			}

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("TeacherProfile");
	}
}
