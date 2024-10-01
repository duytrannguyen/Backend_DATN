package com.poly.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Communes")
public class Commune {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commune_id")
	private Integer commune_id;

	@Column(nullable = false, length = 50)
	String commune_name;

	@ManyToOne
	@JoinColumn(name = "district_id", nullable = false)
	District district;

//	@OneToMany(mappedBy = "commune")
//	private List<Address> addresses;

	@Override
	public String toString() {
		return "Commune{name='" + commune_name + "'}";
	}
}