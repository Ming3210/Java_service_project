package ra.java_service_project.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Full name cannot be empty")
    @Size(max = 50, message = "Full name cannot exceed 50 characters")
    private String fullName;

    @NotNull(message = "Role cannot be null")
    private RoleName role;
}
