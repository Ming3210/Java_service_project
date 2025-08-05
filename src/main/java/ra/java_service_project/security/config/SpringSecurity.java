package ra.java_service_project.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.java_service_project.security.jwt.JWTAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurity {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new JWTAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, JWTAuthFilter jwtAuthFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Public endpoints - không cần xác thực
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Auth endpoints - cần xác thực
                        .requestMatchers(HttpMethod.POST, "/api/auth/verify").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()

                        // User management - chỉ ADMIN
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Course endpoints
                        .requestMatchers(HttpMethod.GET, "/api/courses").authenticated() // Xem danh sách khóa học
                        .requestMatchers(HttpMethod.GET, "/api/courses/{course_id}").authenticated() // Xem chi tiết khóa học
                        .requestMatchers(HttpMethod.POST, "/api/courses").hasRole("ADMIN") // Tạo khóa học mới
                        .requestMatchers(HttpMethod.PUT, "/api/courses/{course_id}").hasRole("ADMIN") // Cập nhật khóa học
                        .requestMatchers(HttpMethod.PUT, "/api/courses/{course_id}/status").hasRole("ADMIN") // Cập nhật trạng thái
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/{course_id}").hasRole("ADMIN") // Xóa khóa học
                        .requestMatchers(HttpMethod.GET, "/api/courses?search={keyword}").authenticated() // Tìm kiếm
                        .requestMatchers(HttpMethod.GET, "/api/courses?teacher_id={teacher_id}").authenticated() // Lọc theo giảng viên
                        .requestMatchers(HttpMethod.GET, "/api/courses?status={status}").authenticated() // Lọc theo trạng thái

                        // Lesson endpoints
                        .requestMatchers(HttpMethod.GET, "/api/courses/{course_id}/lessons").authenticated() // Xem bài học
                        .requestMatchers(HttpMethod.GET, "/api/lessons/{lesson_id}").authenticated() // Chi tiết bài học
                        .requestMatchers(HttpMethod.POST, "/api/courses/{course_id}/lessons").hasAnyRole("TEACHER", "ADMIN") // Thêm bài học
                        .requestMatchers(HttpMethod.PUT, "/api/lessons/{lesson_id}").hasAnyRole("TEACHER", "ADMIN") // Cập nhật bài học
                        .requestMatchers(HttpMethod.PUT, "/api/lessons/{lesson_id}/publish").hasAnyRole("TEACHER", "ADMIN") // Publish bài học
                        .requestMatchers(HttpMethod.DELETE, "/api/lessons/{lesson_id}").hasAnyRole("TEACHER", "ADMIN") // Xóa bài học
                        .requestMatchers(HttpMethod.GET, "/api/lessons/{lesson_id}/content_preview").authenticated() // Xem preview

                        // Enrollment endpoints - STUDENT
                        .requestMatchers(HttpMethod.GET, "/api/enrollments").hasRole("STUDENT") // Xem danh sách đăng ký
                        .requestMatchers(HttpMethod.POST, "/api/enrollments").hasRole("STUDENT") // Đăng ký khóa học
                        .requestMatchers(HttpMethod.GET, "/api/enrollments/{enrollment_id}").hasRole("STUDENT") // Chi tiết đăng ký
                        .requestMatchers(HttpMethod.PUT, "/api/enrollments/{enrollment_id}/complete_lesson/{lesson_id}").hasRole("STUDENT") // Hoàn thành bài học

                        // Review endpoints
                        .requestMatchers(HttpMethod.GET, "/api/courses/{course_id}/reviews").authenticated() // Xem đánh giá
                        .requestMatchers(HttpMethod.POST, "/api/courses/{course_id}/reviews").hasRole("STUDENT") // Gửi đánh giá
                        .requestMatchers(HttpMethod.PUT, "/api/reviews/{review_id}").hasAnyRole("STUDENT", "ADMIN") // Cập nhật đánh giá
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/{review_id}").hasAnyRole("STUDENT", "ADMIN") // Xóa đánh giá

                        // Password management
                        .requestMatchers(HttpMethod.PUT, "/api/users/{user_id}").hasAnyRole("STUDENT", "ADMIN") // Cập nhật thông tin
                        .requestMatchers(HttpMethod.PUT, "/api/users/{user_id}/password").hasAnyRole("STUDENT", "ADMIN") // Đổi mật khẩu

                        // Notification endpoints
                        .requestMatchers(HttpMethod.GET, "/api/notifications").authenticated() // Xem thông báo
                        .requestMatchers(HttpMethod.PUT, "/api/notifications/{notification_id}/read").authenticated() // Đánh dấu đã đọc
                        .requestMatchers(HttpMethod.POST, "/api/notifications").hasRole("ADMIN") // Tạo thông báo
                        .requestMatchers(HttpMethod.DELETE, "/api/notifications/{notification_id}").hasRole("ADMIN") // Xóa thông báo

                        // Reports - chỉ ADMIN
                        .requestMatchers("/api/reports/**").hasRole("ADMIN")

                        // Các endpoint khác yêu cầu xác thực
                        .anyRequest().authenticated()

                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint()))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
