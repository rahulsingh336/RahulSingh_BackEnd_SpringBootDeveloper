package com.decathlon.alert.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.decathlon.alert.util.ApplicationConstants.UUID_REGEX_PATTERN;

@Data
public class SendAlertRequest {

	@NotEmpty(message="TeamEntity Id cant'be empty")
	@Pattern(regexp = UUID_REGEX_PATTERN, message = "team id format is wrong.")
	private String team_id;


}
