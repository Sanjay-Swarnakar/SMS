package sms.teacher.servlet;

import com.sms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import model.AttendanceRecords;
import model.AttendanceSession;

@WebServlet("/Teacher/AttendanceTracker")
public class AttendanceTrackerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = Integer.parseInt(session.getAttribute("id").toString());

        try (Connection con = DBConnection.getConnection()) {
            // Load courses for this teacher
            PreparedStatement psCourses = con.prepareStatement(
                "SELECT c.id, c.name FROM courses c " +
                "JOIN teacher_courses tc ON c.id = tc.course_id " +
                "WHERE tc.teacher_id = ?"
            );
            psCourses.setInt(1, teacherId);
            ResultSet rsCourses = psCourses.executeQuery();

            List<Map<String, Object>> courses = new ArrayList<>();
            while (rsCourses.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("courseId", rsCourses.getInt("id"));
                course.put("courseName", rsCourses.getString("name"));
                courses.add(course);
            }

            // If a course is selected, load its sessions and attendance records
            String selectedCourseIdStr = request.getParameter("courseId");
            int selectedCourseId = selectedCourseIdStr != null ? Integer.parseInt(selectedCourseIdStr) : -1;

            List<AttendanceSession> sessions = new ArrayList<>();
            Map<Integer, List<AttendanceRecords>> attendanceMap = new HashMap<>();

            if (selectedCourseId != -1) {
                // Load sessions for selected course
                PreparedStatement psSessions = con.prepareStatement(
                    "SELECT session_id, session_date, topic FROM attendance_sessions WHERE course_id = ? ORDER BY session_date DESC"
                );
                psSessions.setInt(1, selectedCourseId);
                ResultSet rsSessions = psSessions.executeQuery();

                while (rsSessions.next()) {
                    AttendanceSession sessionObj = new AttendanceSession();
                    sessionObj.setSessionId(rsSessions.getInt("session_id"));
                    sessionObj.setCourseId(selectedCourseId);
                    sessionObj.setSessionDate(rsSessions.getDate("session_date"));
                    sessionObj.setTopic(rsSessions.getString("topic"));
                    sessions.add(sessionObj);
                }

                // Load attendance records for those sessions
                if (!sessions.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("SELECT ar.record_id, ar.session_id, ar.student_id, CONCAT(s.fname, ' ', s.lname) AS student_name, ar.status " +
                              "FROM attendance_records ar JOIN students s ON ar.student_id = s.id " +
                              "WHERE ar.session_id IN (");
                    for (int i = 0; i < sessions.size(); i++) {
                        sb.append("?");
                        if (i < sessions.size() - 1) sb.append(",");
                    }
                    sb.append(")");

                    PreparedStatement psRecords = con.prepareStatement(sb.toString());
                    for (int i = 0; i < sessions.size(); i++) {
                        psRecords.setInt(i + 1, sessions.get(i).getSessionId());
                    }

                    ResultSet rsRecords = psRecords.executeQuery();
                    while (rsRecords.next()) {
                        AttendanceRecords record = new AttendanceRecords();
                        record.setRecordId(rsRecords.getInt("record_id"));
                        record.setSessionId(rsRecords.getInt("session_id"));
                        record.setStudentId(rsRecords.getInt("student_id"));
                        record.setStudentName(rsRecords.getString("student_name"));
                        record.setStatus(rsRecords.getString("status"));

                        attendanceMap.computeIfAbsent(record.getSessionId(), k -> new ArrayList<>()).add(record);
                    }
                }
            }

            request.setAttribute("courses", courses);
            request.setAttribute("selectedCourseId", selectedCourseId);
            request.setAttribute("sessions", sessions);
            request.setAttribute("attendanceMap", attendanceMap);

            request.getRequestDispatcher("/teacher/attendance_tracker.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("DB error", e);
        }
    }

    // Save attendance status updates
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] recordIds = request.getParameterValues("recordId");
        String[] statuses = request.getParameterValues("status");

        if (recordIds != null && statuses != null && recordIds.length == statuses.length) {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "UPDATE attendance_records SET status=? WHERE record_id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                for (int i = 0; i < recordIds.length; i++) {
                    ps.setString(1, statuses[i]);
                    ps.setInt(2, Integer.parseInt(recordIds[i]));
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                throw new ServletException("DB error on update", e);
            }
        }

        // Redirect back with course selected parameter
        String courseId = request.getParameter("courseId");
        response.sendRedirect("AttendanceTracker?courseId=" + courseId);
    }
}
