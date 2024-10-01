
package com.poly.Model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(name = "username", length = 50)
	private String username;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "password")
	private String password;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@Column(name = "gender")
	private Boolean gender;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role roleId;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private UserStatus status;

//	@OneToMany(mappedBy = "user")
//	private List<Seller> sellers;

//	@OneToMany(mappedBy = "user")
//	private List<Address> addresses;

//	@OneToMany(mappedBy = "user")
//	private List<ShoppingCart> shoppingCarts;

//	@OneToMany(mappedBy = "user")
//	private List<SaveInformation> savedInformation;

//	@OneToMany(mappedBy = "user")
//	private List<Invoice> invoices;

//	@OneToMany(mappedBy = "user")
//	private List<DiscountDetail> DiscountDetail;

}
