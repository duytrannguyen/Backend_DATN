package com.poly.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commune {
    private String id;
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String latitude;
    private String longitude;

    public Commune(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
