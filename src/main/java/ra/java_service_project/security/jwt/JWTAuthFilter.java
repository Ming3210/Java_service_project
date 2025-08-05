package ra.java_service_project.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.java_service_project.service.impl.JwtTokenBlacklist;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenBlacklist jwtTokenBlacklist;

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getServletPath();
//        // Bỏ qua JWT cho các endpoint public
//        return  path.startsWith("/api/users");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getTokenFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                if (jwtTokenBlacklist.isBlacklisted(jwt)) {
                    sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Token đã bị thu hồi (logout)");
                    return;
                }

                if (jwtProvider.validateToken(jwt)) {
                    String username = jwtProvider.getUsernameFromToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
                    return;
                }
            }
        } catch (Exception ex) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Xác thực thất bại: " + ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("success", false);
        errorDetails.put("status", status);
        errorDetails.put("message", message);

        new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}