
package com.poly.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usersId;

    @Column(nullable = false)
    @Nationalized
    private String username;

    @Column(nullable = false)
    @Nationalized
    private String fullName;

    @Column(nullable = false)
    @Nationalized
    private String password;

    @Column(nullable = false)
    @Nationalized
    private String profileImage;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(nullable = false)
    private Boolean gender;

    @Column(nullable = false)
    @Nationalized
    private String email;

    @Column(nullable = false)
    @Nationalized
    private String phone;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role roleId;
    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private UserStatus statusId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

    @OneToMany(mappedBy = "users_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<>();

    @Override
    public String toString() {
        return "User{address=" + addresses + "}";
    }

    @OneToMany(mappedBy = "user")
    private List<DiscountDetail> DiscountDetail;

	@OneToMany(mappedBy = "usersId")
	private List<Sellers> sellers;
}
