// ===== JdbcCourseDao.java =====
package dao.impl;

import config.DatabaseConfig;
import dao.CourseDao;
import model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCourseDao implements CourseDao {

    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO course (name, description) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDescription());
            stmt.executeUpdate();

            System.out.println("✓ Cours ajouté avec succès !");

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course ORDER BY id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
                courses.add(course);
            }

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la récupération : " + e.getMessage());
        }

        return courses;
    }

    @Override
    public Course getCourseById(int id) {
        String sql = "SELECT * FROM course WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
            }

        } catch (SQLException e) {
            System.err.println("✗ Erreur : " + e.getMessage());
        }

        return null;
    }
}
