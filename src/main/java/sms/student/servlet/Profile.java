package sms.student.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import model.Student;

/**
 *
 * @author Sanjay Swarnakar
 */
@WebServlet("/Student/Profile")
public class Profile extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Profile.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("id") == null) {
			// Redirect to login or show error
			response.sendRedirect("login.jsp");
			return;
		}
		int id = Integer.parseInt((String) session.getAttribute("id"));
		LOGGER.info(() -> "Session studentId: " + id);

		try (Connection con = DBConnection.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM students s join users u on s.id=u.id WHERE s.id = ?");
			ps.setString(1, (String) session.getAttribute("id"));

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Student student = new Student();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("fname"), rs.getString("mname"), rs.getString("lname"));
				student.setEmail(rs.getString("email"));
				student.setPassword(rs.getString("password"));
				student.setProfilePicture(rs.getString("profile_picture"));

				request.setAttribute("student", student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/student/profile.jsp");
		dispatcher.forward(request, response);
	}
}
