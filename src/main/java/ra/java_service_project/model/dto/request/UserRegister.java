package ra.java_service_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.java_service_project.utils.RoleName;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegister {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private RoleName role;
}
