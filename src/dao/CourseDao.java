package dao;

import model.Course;
import java.util.List;

public interface CourseDao {
    // Ajouter un cours
    void addCourse(Course course);

    // Récupérer tous les cours
    List<Course> getAllCourses();

    // Récupérer un cours par ID
    Course getCourseById(int id);
}