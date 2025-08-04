package sms.teacher.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Message;

@WebServlet("/Teacher/Messages")
public class MessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int teacherId = Integer.parseInt(session.getAttribute("id").toString());

        String receiverType = request.getParameter("receiverType"); // "student" or "parent"
        String receiverIdStr = request.getParameter("receiverId");

        if (receiverType == null || receiverIdStr == null) {
            // Load list of students or parents assigned to teacher to choose conversation
            try (Connection con = DBConnection.getConnection()) {
                // For simplicity, list students of teacher's courses
                PreparedStatement psStudents = con.prepareStatement(
                    "SELECT DISTINCT s.id, CONCAT(s.fname, ' ', s.lname) AS name " +
                    "FROM students s " +
                    "JOIN enrollments e ON s.id = e.student_id " +
                    "JOIN teacher_courses tc ON e.course_id = tc.course_id " +
                    "WHERE tc.teacher_id = ?"
                );
                psStudents.setInt(1, teacherId);
                ResultSet rs = psStudents.executeQuery();

                List<Message> contacts = new ArrayList<>();
                while (rs.next()) {
                    Message contact = new Message();
                    contact.setReceiverId(rs.getInt("id"));
                    contact.setReceiverType("student");
                    contact.setMessageText(rs.getString("name")); // using messageText field to hold name temporarily
                    contacts.add(contact);
                }
                request.setAttribute("contacts", contacts);
                request.getRequestDispatcher("/teacher/message.jsp").forward(request, response);
                return;
            } catch (SQLException e) {
                throw new ServletException("DB error", e);
            }
        } else {
            int receiverId = Integer.parseInt(receiverIdStr);
            // Load messages between teacher and this receiver
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement psMessages = con.prepareStatement(
                    "SELECT message_id, sender_id, receiver_id, receiver_type, message_text, sent_at, is_read " +
                    "FROM messages WHERE (sender_id = ? AND receiver_id = ? AND receiver_type = ?) " +
                    "OR (sender_id = ? AND receiver_id = ? AND receiver_type = ?) " +
                    "ORDER BY sent_at ASC"
                );
                psMessages.setInt(1, teacherId);
                psMessages.setInt(2, receiverId);
                psMessages.setString(3, receiverType);
                psMessages.setInt(4, receiverId);
                psMessages.setInt(5, teacherId);
                psMessages.setString(6, "teacher"); // teacher is senderType for replies? 
                // Actually we only know sender_id and receiver_type — since sender_id is always teacher, just use receiver_type for now
                // For simplicity, we consider messages from teacher to receiver and from receiver to teacher both.

                ResultSet rs = psMessages.executeQuery();

                List<Message> messages = new ArrayList<>();
                while (rs.next()) {
                    Message msg = new Message();
                    msg.setMessageId(rs.getInt("message_id"));
                    msg.setSenderId(rs.getInt("sender_id"));
                    msg.setReceiverId(rs.getInt("receiver_id"));
                    msg.setReceiverType(rs.getString("receiver_type"));
                    msg.setMessageText(rs.getString("message_text"));
                    msg.setSentAt(rs.getTimestamp("sent_at"));
                    msg.setRead(rs.getBoolean("is_read"));
                    messages.add(msg);
                }
                request.setAttribute("messages", messages);
                request.setAttribute("receiverId", receiverId);
                request.setAttribute("receiverType", receiverType);
                request.getRequestDispatcher("messages.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("DB error", e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int teacherId = Integer.parseInt(session.getAttribute("id").toString());

        String receiverType = request.getParameter("receiverType");
        String receiverIdStr = request.getParameter("receiverId");
        String messageText = request.getParameter("messageText");

        if (receiverType == null || receiverIdStr == null || messageText == null || messageText.trim().isEmpty()) {
            response.sendRedirect("Messages");
            return;
        }

        int receiverId = Integer.parseInt(receiverIdStr);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO messages (sender_id, receiver_id, receiver_type, message_text) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, teacherId);
            ps.setInt(2, receiverId);
            ps.setString(3, receiverType);
            ps.setString(4, messageText.trim());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("DB error on insert", e);
        }

        // Redirect to conversation view
        response.sendRedirect("Messages?receiverType=" + receiverType + "&receiverId=" + receiverId);
    }
}
