package com.poly.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.payos.PayOS;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Configuration
public class PayOSConfig {

	@Value("${PAYOS_CLIENT_ID}")
	private String clientId;

	@Value("${PAYOS_API_KEY}")
	private String apiKey;

	@Value("${PAYOS_CHECKSUM_KEY}")
	private String checksumKey;

	@Bean
	public PayOS payOS() {
		return new PayOS(clientId, apiKey, checksumKey);
	}
//
//	@Bean
//	public RestTemplate restTemplate() {
//	    RestTemplate restTemplate = new RestTemplate();
//	    restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
//	    return restTemplate;
//	}

}
