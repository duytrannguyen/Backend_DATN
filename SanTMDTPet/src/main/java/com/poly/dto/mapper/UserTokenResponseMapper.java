
package com.poly.dto.mapper;

import com.poly.Model.User;
import com.poly.dto.response.TokenResponse;

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
                .build();
    }
}
