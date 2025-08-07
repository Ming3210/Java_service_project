package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @Size(max = 50, message = "Type must be at most 50 characters")
    @NotBlank (message = "Type cannot be blank")
    private String type;

    @Size(max = 500, message = "Target URL must be at most 500 characters")
    @NotBlank (message = "Target URL cannot be blank")
    private String targetUrl;
}
