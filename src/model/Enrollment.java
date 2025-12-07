package model;

import java.sql.Timestamp;

public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private Timestamp enrollmentDate;

    // Constructeur vide
    public Enrollment() {}

    // Constructeur avec paramètres
    public Enrollment(int id, int studentId, int courseId, Timestamp enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    // Constructeur pour nouvelle inscription
    public Enrollment(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public Timestamp getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Timestamp enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}