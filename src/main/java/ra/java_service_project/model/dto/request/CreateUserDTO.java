package ra.java_service_project.model.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.RoleName;
import ra.java_service_project.validation.EmailValidator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3–50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @EmailValidator
    private String email;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @NotNull(message = "Role is required")
    private RoleName role;
}
