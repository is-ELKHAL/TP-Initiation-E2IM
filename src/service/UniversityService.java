// ===== UniversityService.java =====
package service;

import dao.CourseDao;
import dao.EnrollmentDao;
import dao.StudentDao;
import dao.impl.JdbcCourseDao;
import dao.impl.JdbcEnrollmentDao;
import dao.impl.JdbcStudentDao;
import model.Course;
import model.Enrollment;
import model.Student;
import java.util.List;

/**
 * Service qui regroupe toutes les opérations de haut niveau
 * C'est la couche qui fait le lien entre l'interface utilisateur et les DAO
 */
public class UniversityService {

    // Instances des DAO
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final EnrollmentDao enrollmentDao;

    // Constructeur : initialise les DAO
    public UniversityService() {
        this.studentDao = new JdbcStudentDao();
        this.courseDao = new JdbcCourseDao();
        this.enrollmentDao = new JdbcEnrollmentDao();
    }

    // ========== OPÉRATIONS SUR LES ÉTUDIANTS ==========

    /**
     * Ajoute un nouvel étudiant
     */
    public void addStudent(String name, String email) {
        Student student = new Student(name, email);
        studentDao.addStudent(student);
    }

    /**
     * Récupère tous les étudiants
     */
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    /**
     * Affiche la liste des étudiants
     */
    public void listStudents() {
        List<Student> students = getAllStudents();

        if (students.isEmpty()) {
            System.out.println("Aucun étudiant enregistré.");
            return;
        }

        System.out.println("\n===== LISTE DES ÉTUDIANTS =====");
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("==============================\n");
    }

    /**
     * Supprime un étudiant et toutes ses inscriptions
     */
    public void deleteStudent(int studentId) {
        // Vérifie si l'étudiant existe
        Student student = studentDao.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Aucun étudiant trouvé avec l'ID " + studentId);
            return;
        }

        // Supprime d'abord les inscriptions
        enrollmentDao.deleteEnrollmentsByStudent(studentId);

        // Puis supprime l'étudiant
        studentDao.deleteStudent(studentId);
    }

    // ========== OPÉRATIONS SUR LES COURS ==========

    /**
     * Ajoute un nouveau cours
     */
    public void addCourse(String name, String description) {
        Course course = new Course(name, description);
        courseDao.addCourse(course);
    }

    /**
     * Récupère tous les cours
     */
    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    /**
     * Affiche la liste des cours
     */
    public void listCourses() {
        List<Course> courses = getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("Aucun cours disponible.");
            return;
        }

        System.out.println("\n===== LISTE DES COURS =====");
        for (Course course : courses) {
            System.out.println(course);
        }
        System.out.println("===========================\n");
    }

    // ========== OPÉRATIONS SUR LES INSCRIPTIONS ==========

    /**
     * Inscrit un étudiant à un cours
     */
    public void enrollStudent(int studentId, int courseId) {
        // Vérifie si l'étudiant existe
        Student student = studentDao.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Étudiant introuvable avec l'ID " + studentId);
            return;
        }

        // Vérifie si le cours existe
        Course course = courseDao.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Cours introuvable avec l'ID " + courseId);
            return;
        }

        // Crée l'inscription
        Enrollment enrollment = new Enrollment(studentId, courseId);
        enrollmentDao.enrollStudent(enrollment);
    }

    /**
     * Affiche tous les cours d'un étudiant
     */
    public void listStudentCourses(int studentId) {
        // Vérifie si l'étudiant existe
        Student student = studentDao.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Étudiant introuvable avec l'ID " + studentId);
            return;
        }

        // Récupère les cours
        List<Course> courses = enrollmentDao.getStudentCourses(studentId);

        System.out.println("\n===== COURS DE " + student.getName() + " =====");

        if (courses.isEmpty()) {
            System.out.println("Cet étudiant n'est inscrit à aucun cours.");
        } else {
            for (Course course : courses) {
                System.out.println("- " + course.getName() + " : " + course.getDescription());
            }
        }

        System.out.println("================================\n");
    }
}