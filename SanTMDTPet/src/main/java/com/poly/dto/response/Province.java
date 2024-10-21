package com.poly.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Province {
    private String id;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String latitude;
    private String longitude;
}
