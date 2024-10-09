package com.poly.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Orderstatus")
public class OrderStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statusId;

	@Column(nullable = false)
	@Nationalized
	private String statusName;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date estimated_delivery_date;

}
