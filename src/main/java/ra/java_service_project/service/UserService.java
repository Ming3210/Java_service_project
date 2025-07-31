package ra.java_service_project.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import ra.java_service_project.model.dto.request.UserLogin;
import ra.java_service_project.model.dto.request.UserProfileRequest;
import ra.java_service_project.model.dto.request.UserRegister;
import ra.java_service_project.model.dto.response.JWTResponse;
import ra.java_service_project.model.entity.User;

public interface UserService {
    User register(UserRegister userRegister);

    JWTResponse login(UserLogin userLogin);

    UserProfileRequest getCurrentUserProfile(Authentication authentication);

    Page<User> getAllUsers(Integer page, Integer itemPage, String sortBy, Boolean orderBy);

    User findById(Integer id);

    User createUser(User user);

    User updateUser(User user, Integer id);

    void deleteUser(Integer id);


}
