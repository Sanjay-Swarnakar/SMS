package model;

public class Grade {
    private String subject;
    private String grade;
    private String remarks;

    public Grade(String subject, String grade, String remarks) {
        this.subject = subject;
        this.grade = grade;
        this.remarks = remarks;
    }

    public String getSubject() {
        return subject;
    }

    public String getGrade() {
        return grade;
    }

    public String getRemarks() {
        return remarks;
    }
}
