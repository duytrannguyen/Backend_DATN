package com.poly.Mapper;

import com.poly.dto.response.TokenResponse;
import com.poly.Model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserTokenResponseMapper implements Function<User, TokenResponse> {

    @Override
    public TokenResponse apply(User user) {
        return null;
    }

    public TokenResponse mapToTokenResponse(User user, String token) {
        return TokenResponse.builder()
                .token(token)
                .userName(user.getUsername())
                .roleName(user.getRoleId().getRoleName())
                .build();
    }
}
