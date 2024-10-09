package com.poly.dto.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.poly.Model.User;
import com.poly.dto.response.UserResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDTOMapper implements Function<User, UserResponse> {

    private final HttpServletRequest request;

    @Override
    public UserResponse apply(User user) {
        return UserResponse.builder()
                .usersId(user.getUsersId())
                .userName(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .avatarProfile(user.getProfileImage()!=null ? getFullDomain() + "/api/v1/load-image/" + user.getProfileImage() : null)
                .gender(user.getGender())
                .build();
    }
    public UserResponse applyUserGG(User user) {
        return UserResponse.builder()
                .usersId(user.getUsersId())
                .userName(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .avatarProfile(user.getProfileImage())
                .gender(user.getGender())
                .build();
    }

    public String getFullDomain() {
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort();
    }
}