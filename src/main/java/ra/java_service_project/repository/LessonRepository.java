package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.entity.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findLessonsByCourse_CourseId(Integer courseCourseId);


}
