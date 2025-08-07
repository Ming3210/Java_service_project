package ra.java_service_project.model.dto.request;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private Integer courseId;

    private Integer userId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    @NotBlank(message = "Comment is required")
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
