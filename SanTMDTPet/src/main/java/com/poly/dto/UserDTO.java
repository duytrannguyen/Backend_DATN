package com.poly.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer usersId;
    private String username;
    private String fullName;
    private String password; // Nếu bạn không muốn gửi mật khẩu, có thể bỏ trường này
    private String profileImage;
    private Date birthDate;
    private Boolean gender;
    private String email;
    private String phone;
    private Integer roleId; // Chỉ lưu id của role, không cần đối tượng Role
    private Integer statusId; // Chỉ lưu id của status, không cần đối tượng UserStatus
}
