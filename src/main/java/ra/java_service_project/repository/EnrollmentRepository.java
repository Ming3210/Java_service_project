package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.model.entity.Enrollment;
import ra.java_service_project.model.entity.LessonProgress;
import ra.java_service_project.model.entity.User;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findEnrollmentsByStudent_Username(String studentUsername);

    boolean existsByStudentAndCourse(User student, Course course);

    List<Enrollment> findEnrollmentsByCourse_Title(String courseTitle);
}
