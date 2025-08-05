package ra.java_service_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.CourseStatus;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseStats {
    private Long totalCourses;
    private Long totalStudents;
    private Long publishedCourses;
    private Double averageRating;
    private Map<String, Long> studentsPerCourse;
}