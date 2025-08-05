package ra.java_service_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.java_service_project.model.dto.response.CourseResponse;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.utils.CourseStatus;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findCoursesByTitleContainingIgnoreCase(String title);

    List<Course> findCoursesByTeacher_UserId(Integer teacherUserId);

    List<Course> findCoursesByStatus(CourseStatus status);

    @Query("""
    SELECT new ra.java_service_project.model.dto.response.CourseResponse(
        c.courseId,
        c.title,
        c.description,
        c.price,
        c.durationHours,
        c.status,
        c.teacher.userId,
        c.createdAt,
        c.updatedAt,
        COUNT(e)
    )
    FROM Course c
    LEFT JOIN c.enrollments e
    GROUP BY c.courseId, c.title, c.description, c.price, c.durationHours,
             c.status, c.teacher.userId, c.createdAt, c.updatedAt
    ORDER BY COUNT(e) DESC
""")
    List<CourseResponse> findCoursesOrderByEnrollmentsDesc();


    @Query("SELECT COUNT(c) FROM Course c WHERE c.teacher.userId = :teacherId")
    Long countCoursesByTeacher(@Param("teacherId") Integer teacherId);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.teacher.userId = :teacherId AND c.status = 'PUBLISHED'")
    Long countPublishedCourses(@Param("teacherId") Integer teacherId);

    @Query("SELECT COUNT(DISTINCT e.student.userId) FROM Enrollment e WHERE e.course.teacher.userId = :teacherId")
    Long countTotalStudents(@Param("teacherId") Integer teacherId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.course.teacher.userId = :teacherId")
    Double getAverageRating(@Param("teacherId") Integer teacherId);

    @Query("SELECT c.title as courseName, COUNT(e) as studentCount FROM Course c JOIN c.enrollments e WHERE c.teacher.userId = :teacherId GROUP BY c.title")
    List<Object[]> getStudentsPerCourse(@Param("teacherId") Integer teacherId);
}
