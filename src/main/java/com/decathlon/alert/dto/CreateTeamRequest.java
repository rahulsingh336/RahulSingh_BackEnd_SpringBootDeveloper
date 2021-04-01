package com.decathlon.alert.dto;

import com.decathlon.alert.validator.MinSizeConstraint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateTeamRequest {

	@NotEmpty(message="TeamEntity Name cant'be empty")
	private String teamName;

	@Valid
	@MinSizeConstraint
	private List<DeveloperRequest> developers = new ArrayList<>();
}
