package model;

public class Assignment {
    private int id;
    private String title;
    private String subject;
    private String fileName;
    private String dueDate;
    private boolean submitted;

    public Assignment(int id, String title, String subject, String fileName, String dueDate, boolean submitted) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.fileName = fileName;
        this.dueDate = dueDate;
        this.submitted = submitted;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public String getFileName() { return fileName; }
    public String getDueDate() { return dueDate; }
    public boolean isSubmitted() { return submitted; }
}
