package com.poly.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserResponse {

    private Integer usersId;
    private String userName;
    private String fullName;
    private String role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Boolean gender;
    private String email;
    private String phone;
    private String avatarProfile;
    private Integer sellerId;
}
