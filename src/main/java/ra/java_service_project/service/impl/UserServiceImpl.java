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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.UserLogin;
import ra.java_service_project.model.dto.request.UserProfileRequest;
import ra.java_service_project.model.dto.request.UserRegister;
import ra.java_service_project.model.dto.response.JWTResponse;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.repository.UserRepository;
import ra.java_service_project.security.jwt.JWTAuthFilter;
import ra.java_service_project.security.jwt.JWTProvider;
import ra.java_service_project.security.principal.UserPrincipal;
import ra.java_service_project.service.UserService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;



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
                .role(userRegister.getRole())
                .build();
        return userRepository.save(user);
    }

    @Override
    public JWTResponse login(UserLogin userLogin) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
        }catch(AuthenticationException e){
            log.error("Sai username hoac password!");
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
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Integer id) {
        user.setUserId(id);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


}
