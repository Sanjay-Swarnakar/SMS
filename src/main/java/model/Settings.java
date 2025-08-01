package model;

public class Settings {
    private String academicYear;
    private String holidays;
    private String defaultRole;

    public Settings(String academicYear, String holidays, String defaultRole) {
        this.academicYear = academicYear;
        this.holidays = holidays;
        this.defaultRole = defaultRole;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getHolidays() {
        return holidays;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }
}
