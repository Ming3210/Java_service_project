package ra.java_service_project.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.RoleName;
import ra.java_service_project.validation.EmailValidator;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegister {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @EmailValidator
    private String email;

    @NotBlank(message = "Full name cannot be empty")
    @Size(max = 50, message = "Full name cannot exceed 50 characters")
    private String fullName;

}
