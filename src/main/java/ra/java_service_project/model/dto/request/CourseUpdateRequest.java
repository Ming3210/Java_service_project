package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.CourseStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseUpdateRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private Integer durationHours;
    private CourseStatus status;
    private Integer teacherId;
}
