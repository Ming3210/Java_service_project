package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.EnrollmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Integer courseId;

    private String courseTitle;

    private LocalDateTime enrollmentDate;

    private EnrollmentStatus status;

    private LocalDateTime completionDate;

    @DecimalMin(value = "0.00", message = "Progress must be >= 0")
    @DecimalMax(value = "100.00", message = "Progress must be <= 100")
    private BigDecimal progressPercentage;
}
