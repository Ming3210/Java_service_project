package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.EnrollmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private Integer courseId;
    private String courseTitle;
    private LocalDateTime enrollmentDate;
    private EnrollmentStatus status;
    private LocalDateTime completionDate;
    private BigDecimal progressPercentage;
}
