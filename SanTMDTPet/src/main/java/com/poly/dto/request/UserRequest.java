package com.poly.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private int usersId;
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean gender;
    private String birthDate;
    private String idProvince;
    private String province;
    private String idDistrict;
    private String district;
    private String idCommune;
    private String commune;
    private String streetName;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
    private String roleName;

    @Override
    public String toString() {
        return "UserRequest{" +
                "usersId=" + usersId +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", birthDate='" + birthDate + '\'' +
                ", idProvince='" + idProvince + '\'' +
                ", province='" + province + '\'' +
                ", idDistrict='" + idDistrict + '\'' +
                ", district='" + district + '\'' +
                ", idCommune='" + idCommune + '\'' +
                ", commune='" + commune + '\'' +
                ", streetName='" + streetName + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                 ", roleName='" + roleName + '\'' +
                '}';
    }
}
