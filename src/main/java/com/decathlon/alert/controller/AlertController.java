package com.decathlon.alert.controller;

import com.decathlon.alert.dto.CreateTeamRequest;
import com.decathlon.alert.dto.CreateTeamResponse;
import com.decathlon.alert.dto.SMSServiceResponse;
import com.decathlon.alert.dto.SendAlertRequest;
import com.decathlon.alert.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@Slf4j
public class AlertController {

	private AlertService alertService;

	public AlertController(AlertService alertService) {
		this.alertService = alertService;
	}

	@PostMapping("/team")
	public ResponseEntity<CreateTeamResponse> createTeam(@Valid @RequestBody CreateTeamRequest createTeamRequest) {
		log.info("Creating team for :::: {}", createTeamRequest.getTeamName());
		return new ResponseEntity(alertService.createTeam(createTeamRequest), HttpStatus.CREATED);
	}

	@PostMapping("/{team_id}/alert")
	public ResponseEntity sendAlert(@Valid SendAlertRequest sendAlertRequest) {
		SMSServiceResponse smsServiceResponse = alertService.sendAlert(sendAlertRequest.getTeam_id());
		return new ResponseEntity(smsServiceResponse, HttpStatus.OK);
	}

}

