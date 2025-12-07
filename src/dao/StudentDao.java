
// ===== StudentDao.java =====
package dao;

import model.Student;
import java.util.List;

public interface StudentDao {
    // Ajouter un étudiant
    void addStudent(Student student);

    // Récupérer tous les étudiants
    List<Student> getAllStudents();

    // Récupérer un étudiant par ID
    Student getStudentById(int id);

    // Supprimer un étudiant
    void deleteStudent(int id);
}