package com.poly.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class District {
    private String id;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String latitude;
    private String longitude;

    public District(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
