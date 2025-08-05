package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.model.entity.Lesson;
import ra.java_service_project.model.entity.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Optional<Object> findByCourse(Course course);

    List<Review> findByCourse_CourseId(Integer courseCourseId);
}
