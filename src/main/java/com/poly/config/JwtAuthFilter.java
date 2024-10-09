package com.poly.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.poly.serviceAPI.JwtService;
import com.poly.serviceAPI.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Đảm bảo rằng chỉ một khai báo bean
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService; // Tự động tiêm đối tượng JwtService

    @Autowired
    private UserService userService; // Tự động tiêm đối tượng UserService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy giá trị của header Authorization từ yêu cầu HTTP
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Kiểm tra xem header Authorization có tồn tại và bắt đầu với "Bearer " không
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Lấy JWT từ header Authorization
            token = authHeader.substring(7);
            // Trích xuất username từ JWT
            username = jwtService.extractUsername(token);
            System.out.println("username " + username);
        }

        // Nếu username không null và chưa có đối tượng Authentication trong SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Tải thông tin chi tiết của người dùng từ UserService
            UserDetails userDetails = userService.loadUserByUsername(username);
            // Kiểm tra tính hợp lệ của token
            if (jwtService.validateToken(token, userDetails)) {
                // Tạo đối tượng Authentication và thiết lập vào SecurityContext
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // Tiếp tục thực hiện các bộ lọc khác trong FilterChain
        filterChain.doFilter(request, response);
    }
}
