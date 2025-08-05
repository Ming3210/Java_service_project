package ra.java_service_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationResponse {
    private Integer notificationId;
    private Integer userId;
    private String message;
    private String type;
    private String targetUrl;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
