package ra.java_service_project.model.dto.response;

import lombok.*;
import ra.java_service_project.utils.CourseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseResponse {
    private Integer courseId;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer durationHours;
    private CourseStatus status;
    private Integer teacherId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long enrollmentCount;

}
