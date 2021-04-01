package com.decathlon.alert.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeveloperRequest {

	@NotEmpty(message="DeveloperEntity Name cant'be empty")
	private String name;

	@NotEmpty(message="Phone Number cant'be empty")
	private String phoneNumber;
}
