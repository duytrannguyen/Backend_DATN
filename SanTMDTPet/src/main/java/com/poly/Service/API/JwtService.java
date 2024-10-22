package com.poly.Service.API;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service // Đánh dấu lớp này là một service của Spring
public class JwtService {
    // Khóa bí mật để ký JWT (nên lấy từ biến môi trường hoặc file cấu hình)
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    // Trích xuất tên người dùng từ JWT
    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            return null; // Hoặc xử lý lỗi theo cách khác
        }
    }

    // Trích xuất một claim từ JWT bằng cách sử dụng một hàm resolver
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Trích xuất tất cả các claims từ JWT
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey()) // Thiết lập khóa ký để giải mã JWT
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Lấy khóa ký từ khóa bí mật
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Trích xuất ngày hết hạn từ JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Kiểm tra xem JWT có hết hạn hay không
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Xác thực JWT với chi tiết người dùng
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Tạo JWT mới với tên người dùng
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Tạo JWT với claims và tên người dùng
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) // Thiết lập các claims
                .setSubject(username) // Thiết lập chủ thể là tên người dùng
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thiết lập ngày phát hành
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // Thiết lập ngày hết hạn
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Ký JWT với khóa và thuật toán HS256
                .compact(); // Tạo JWT
    }
}
