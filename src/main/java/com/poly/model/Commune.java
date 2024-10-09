package com.poly.model;

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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Communes")
public class Commune {
	@Id
	private Integer commune_id;

	@Column(nullable = false, length = 50)
	@Nationalized
	String commune_name;

	@ManyToOne
	@JoinColumn(name = "district_id", nullable = false)
	District district;

	@Override
	public String toString() {
		return "Commune{name='" + commune_name + "'}";
	}
}