package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.dto.response.StudentProgressResponse;

import ra.java_service_project.model.dto.response.TeacherCourseStats;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.utils.RoleName;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(RoleName role);

    List<User> findByIsActive(Boolean isActive);

    @Query("""
    SELECT new ra.java_service_project.model.dto.response.StudentProgressResponse(
        e.course.courseId,
        e.course.title,
        e.progressPercentage
    )
    FROM Enrollment e
    WHERE e.student.userId = :studentId
""")
    List<StudentProgressResponse> getStudentProgress(@Param("studentId") Integer studentId);




}
