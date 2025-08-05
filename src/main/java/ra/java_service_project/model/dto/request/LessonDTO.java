package ra.java_service_project.model.dto.request;

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
    private String title;
    private String contentUrl;
    private String textContent;
    private Integer orderIndex;
    private Boolean isPublished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer courseId;
}
