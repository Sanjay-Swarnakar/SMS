package sms.teacher.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import sms.model.Teacher;

@WebServlet("/Teacher/TeacherProfile")
public class TeacherProfile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			response.sendRedirect("/SMS/login.jsp");
			return;
		}

		int teacherId = Integer.parseInt((String)session.getAttribute("id"));

		try (Connection con = DBConnection.getConnection()) {
			String sql = "SELECT * FROM teachers WHERE id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, teacherId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("fname"));
				t.setEmail(rs.getString("email"));
				t.setPassword(rs.getString("password"));
				t.setContact(rs.getString("contact"));
				t.setQualification(rs.getString("qualification"));
				t.setProfilePicture(rs.getString("profile_picture"));

				request.setAttribute("teacher", t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("/teacher/teacher_profile.jsp").forward(request, response);
	}
}
