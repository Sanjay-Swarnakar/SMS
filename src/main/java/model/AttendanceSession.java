package model;

import java.util.Date;

public class AttendanceSession {
    private int sessionId;
    private int courseId;
    private Date sessionDate;
    private String topic;

    // Getters and setters
    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public Date getSessionDate() { return sessionDate; }
    public void setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
