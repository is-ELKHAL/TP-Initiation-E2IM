package dao.impl;

import config.DatabaseConfig;
import dao.EnrollmentDao;
import model.Course;
import model.Enrollment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEnrollmentDao implements EnrollmentDao {

    @Override
    public void enrollStudent(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.executeUpdate();

            System.out.println("✓ Inscription effectuée avec succès !");

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("✗ Cet étudiant est déjà inscrit à ce cours !");
            } else {
                System.err.println("✗ Erreur lors de l'inscription : " + e.getMessage());
            }
        }
    }

    @Override
    public List<Course> getStudentCourses(int studentId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.* FROM course c " +
                "INNER JOIN enrollment e ON c.id = e.course_id " +
                "WHERE e.student_id = ? ORDER BY c.name";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
                courses.add(course);
            }

        } catch (SQLException e) {
            System.err.println("✗ Erreur : " + e.getMessage());
        }

        return courses;
    }

    @Override
    public void deleteEnrollmentsByStudent(int studentId) {
        String sql = "DELETE FROM enrollment WHERE student_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la suppression des inscriptions : "
                    + e.getMessage());
        }
    }
}