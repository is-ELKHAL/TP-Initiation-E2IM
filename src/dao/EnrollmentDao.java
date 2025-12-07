package dao;

import model.Course;
import model.Enrollment;
import java.util.List;

public interface EnrollmentDao {
    // Inscrire un étudiant à un cours
    void enrollStudent(Enrollment enrollment);

    // Récupérer les cours d'un étudiant
    List<Course> getStudentCourses(int studentId);

    // Supprimer toutes les inscriptions d'un étudiant
    void deleteEnrollmentsByStudent(int studentId);
}