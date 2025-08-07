package ra.java_service_project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.CreateUserDTO;
import ra.java_service_project.model.dto.request.UserLogin;
import ra.java_service_project.model.dto.request.UserProfileRequest;
import ra.java_service_project.model.dto.request.UserRegister;
import ra.java_service_project.model.dto.response.JWTResponse;
import ra.java_service_project.model.dto.response.StudentProgressResponse;
import ra.java_service_project.model.dto.response.TeacherCourseStats;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.repository.UserRepository;
import ra.java_service_project.security.jwt.JWTAuthFilter;
import ra.java_service_project.security.jwt.JWTProvider;
import ra.java_service_project.security.principal.UserPrincipal;
import ra.java_service_project.service.UserService;
import ra.java_service_project.utils.RoleName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenBlacklist jwtTokenBlacklist;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTAuthFilter jWTAuthFilter;


    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public User register(UserRegister userRegister) {
        User user = User.builder()
                .username(userRegister.getUsername())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .fullName(userRegister.getFullName())
                .email(userRegister.getEmail())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(RoleName.ROLE_STUDENT)
                .build();
        return userRepository.save(user);
    }

    @Override
    public JWTResponse login(UserLogin userLogin) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
            );

        } catch (AuthenticationException e) {
            log.error("Sai username hoặc password!", e);
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không đúng");
        }

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user.getUsername());

        return JWTResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .isActive(true)
                .token(token)
                .build();
    }


    public UserProfileRequest getCurrentUserProfile(Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserProfileRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    @Override
    public Page<User> getAllUsers(Integer page, Integer itemPage, String sortBy, Boolean orderBy) {
        Pageable pageable = null;
        Sort sort = null;
        if(sortBy!=null && !sortBy.isEmpty()) {
            if(orderBy){
                sort = Sort.by(Sort.Direction.ASC,sortBy);
            }else{
                sort = Sort.by(Sort.Direction.DESC,sortBy);
            }
            pageable = PageRequest.of(page,itemPage,sort);
        }else{
            pageable = PageRequest.of(page,itemPage);
        }
        return userRepository.findAll(pageable);

    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User not found"));
    }

    @Override
    public User createUser(CreateUserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user, Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!currentUser.getUserId().equals(id)) {
            throw new SecurityException("You are not allowed to update another user");
        }

        if(!currentUser.getRole().name().equals("ADMIN")){
            user.setRole(currentUser.getRole());
        }
        user.setUserId(id);
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(currentUser.getCreatedAt());
        user.setPassword(currentUser.getPassword());

        return userRepository.save(user);
    }


    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean updatePassword(String newPassword, Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!currentUser.getUserId().equals(id)) {
            throw new SecurityException("Bạn không có quyền đổi mật khẩu của user khác");
        }
        log.info(newPassword);

        String encodedPassword = passwordEncoder.encode(newPassword);
        currentUser.setPassword(encodedPassword);
        currentUser.setUpdatedAt(LocalDateTime.now());
        // Thêm log để kiểm tra
        log.info("New encoded password: " + encodedPassword);
        log.info("Old password: " + currentUser.getPassword());
        userRepository.save(currentUser);
        return true;
    }

    @Override
    public Boolean logout(String token) {
        if (jwtProvider.validateToken(token)) {
            jwtTokenBlacklist.addToBlacklist(token);
            return true;
        }
        return false;
    }

    @Override
    public List<UserProfileRequest> getUsersByStatus(Boolean status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!currentUser.getRole().name().equals("ROLE_ADMIN")) {
            throw new SecurityException("You are not allowed to get users by role");
        }
        List<User> users = userRepository.findByIsActive(status);
        return users.stream()
                .map(user -> UserProfileRequest.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentProgressResponse> getStudentProgress(Integer studentId) {
        return userRepository.getStudentProgress(studentId);
    }





}
