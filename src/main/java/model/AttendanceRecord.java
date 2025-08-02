package model;

public class AttendanceRecord {
    private String subject;
    private int attended;
    private int total;

    public AttendanceRecord(String subject, int attended, int total) {
        this.subject = subject;
        this.attended = attended;
        this.total = total;
    }

    public String getSubject() {
        return subject;
    }

    public int getAttended() {
        return attended;
    }

    public int getTotal() {
        return total;
    }

    public double getPercentage() {
        return total == 0 ? 0.0 : ((double) attended / total) * 100;
    }
}
