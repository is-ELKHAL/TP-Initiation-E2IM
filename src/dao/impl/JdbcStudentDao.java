// ===== JdbcStudentDao.java =====
package dao.impl;

import config.DatabaseConfig;
import dao.StudentDao;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcStudentDao implements StudentDao {

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO student (name, email) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.executeUpdate();

            System.out.println("✓ Étudiant ajouté avec succès !");

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la récupération : " + e.getMessage());
        }

        return students;
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            System.err.println("✗ Erreur : " + e.getMessage());
        }

        return null;
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✓ Étudiant supprimé avec succès !");
            } else {
                System.out.println("✗ Aucun étudiant trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.err.println("✗ Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
