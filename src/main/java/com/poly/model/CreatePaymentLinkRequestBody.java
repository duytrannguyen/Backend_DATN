package com.poly.model;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreatePaymentLinkRequestBody {
	  private String productName;
	  private String description;
	  private String returnUrl;
	  private int amount;
	  private String cancelUrl;

	}


