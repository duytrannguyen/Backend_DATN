package com.poly.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Address") // Gắn với bảng 'Address' trong cơ sở dữ liệu
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id") // Tên cột trong cơ sở dữ liệu
    private Integer id; // ID địa chỉ

    @Column(name = "status") // Trạng thái (mặc định/ không mặc định)
    private boolean status;

    @Column(name = "street", nullable = false) // Tên đường
    private String street;

    @Column(name = "full_address") // Địa chỉ cụ thể
    private String fullAddress; // Đường + xã/phường + quận/huyện + tỉnh/t.phố

   
    @JoinColumn(name = "commune_id") // ID xã/phường
    private int commune;


    @JoinColumn(name = "district_id") // ID quận/huyện
    private int district;


    @JoinColumn(name = "province_id") // ID tỉnh/thành phố
    private int province;

    @ManyToOne
    @JoinColumn(name = "users_id") // ID người dùng
    private User user;
}
