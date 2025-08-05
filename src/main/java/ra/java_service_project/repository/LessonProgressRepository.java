package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.entity.LessonProgress;
@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Integer> {
    LessonProgress findLessonProgressByEnrollment_EnrollmentId(Integer enrollmentEnrollmentId);

    LessonProgress findLessonProgressByEnrollment_EnrollmentIdAndLesson_LessonId(Integer enrollmentEnrollmentId, Integer lessonLessonId);
}
