package model;

import java.sql.Timestamp;

public class SystemLog {
    private int id;
    private String logType;
    private String message;
    private String user;
    private Timestamp timestamp;

    public SystemLog(int id, String logType, String message, String user, Timestamp timestamp) {
        this.id = id;
        this.logType = logType;
        this.message = message;
        this.user = user;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public String getLogType() { return logType; }
    public String getMessage() { return message; }
    public String getUser() { return user; }
    public Timestamp getTimestamp() { return timestamp; }
}
