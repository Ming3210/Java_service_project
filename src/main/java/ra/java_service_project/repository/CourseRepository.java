package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
