package ra.java_service_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.UserLogin;
import ra.java_service_project.model.dto.request.UserProfileRequest;
import ra.java_service_project.model.dto.request.UserRegister;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.dto.response.JWTResponse;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.security.jwt.JWTProvider;
import ra.java_service_project.service.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserService userService;



    @Autowired
    private JWTProvider jwtProvider;


    @PostMapping("/register")
    public ResponseEntity<APIDataResponse<User>> register(@Valid @RequestBody UserRegister userRegister) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.register(userRegister), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<APIDataResponse<JWTResponse>> login(@Valid @RequestBody UserLogin userLogin) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.login(userLogin), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<APIDataResponse<UserProfileRequest>> getProfile(Authentication authentication) {
        UserProfileRequest profile = userService.getCurrentUserProfile(authentication);
        return ResponseEntity.ok(new APIDataResponse<>(true, "OK", profile, HttpStatus.OK));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        // Remove "Bearer " prefix
        String token = bearerToken.substring(7);
        userService.logout(token);
        return ResponseEntity.ok(new APIDataResponse<>(true, "Đăng xuất thành công",
                null, HttpStatus.OK));
    }

}
