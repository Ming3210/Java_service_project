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
public class LessonDTO {

    private Integer lessonId;

    @NotBlank(message = "Lesson title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 500, message = "Content URL must not exceed 500 characters")
    private String contentUrl;

    private String textContent;

    @NotNull(message = "Order index is required")
    @PositiveOrZero(message = "Order index must be 0 or greater")
    private Integer orderIndex;

    @NotNull(message = "Publish status is required")
    private Boolean isPublished;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be a positive number")
    private Integer courseId;
}
