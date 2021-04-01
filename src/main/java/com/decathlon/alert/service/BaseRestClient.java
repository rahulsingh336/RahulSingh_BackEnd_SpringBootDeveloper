package com.decathlon.alert.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

import static com.decathlon.alert.util.ApplicationConstants.SMS_ALERT_URL;

@Slf4j
public abstract class BaseRestClient implements Serializable {

	private static final long serialVersionUID = 11748340711395755L;

	@Autowired
	private RestTemplate restTemplate;

	public <T> T invokeService(ParameterizedTypeReference<T> typeRef, Object payload, HttpMethod method) {
		HttpHeaders headers = this.getHeaders();
		HttpEntity httpEntity;
		if (payload != null) {
			httpEntity = new HttpEntity(payload, headers);
		} else {
			httpEntity = new HttpEntity(headers);
		}
		log.info("Calling External service, URL is {}", SMS_ALERT_URL);
		ResponseEntity<T> response = this.getRestTemplate().exchange(SMS_ALERT_URL, method, httpEntity, typeRef, new Object[0]);
		log.info("Response from external service is :::: {}", response);
		return response == null ? null : response.getBody();
	}

	private RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
