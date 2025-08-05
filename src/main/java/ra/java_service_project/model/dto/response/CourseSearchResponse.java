package ra.java_service_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.CourseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchResponse {
    private Integer courseId;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer durationHours;
    private CourseStatus status;
    private Integer teacherId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
