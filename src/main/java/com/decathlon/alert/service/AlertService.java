package com.decathlon.alert.service;

import com.decathlon.alert.dto.CreateTeamRequest;
import com.decathlon.alert.dto.CreateTeamResponse;
import com.decathlon.alert.dto.SMSServiceResponse;
import com.decathlon.alert.exception.NoDataFoundException;
import com.decathlon.alert.exception.TeamAlreadyExistsException;
import com.decathlon.alert.model.DeveloperEntity;
import com.decathlon.alert.model.TeamEntity;
import com.decathlon.alert.repository.DeveloperRepository;
import com.decathlon.alert.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlertService {

	private TeamRepository teamRepository;
	private InvokeSMSService invokeSMSService;
	private DeveloperRepository developerRepository;

	@Autowired
	public AlertService(TeamRepository teamRepository, InvokeSMSService invokeSMSService, DeveloperRepository developerRepository) {
		this.teamRepository = teamRepository;
		this.invokeSMSService = invokeSMSService;
		this.developerRepository = developerRepository;
	}

	public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest) {
		log.info("Creating teamEntity for :::: {}", createTeamRequest.getTeamName());

		teamRepository.findByTeamName(createTeamRequest.getTeamName()).ifPresent(teamEntity -> {
			throw new TeamAlreadyExistsException(createTeamRequest.getTeamName());
		});

		TeamEntity teamEntity = convertToModel(createTeamRequest);

		log.info("Saving teamEntity for :::: {}", createTeamRequest.getTeamName());
		TeamEntity teamEntitySaved = teamRepository.save(teamEntity);

		CreateTeamResponse createTeamResponse = new CreateTeamResponse();
        createTeamResponse.setTeamId(teamEntitySaved.getId().toString());

        log.info("TeamEntity id created is :::: {}", createTeamResponse.getTeamId());
		return createTeamResponse;
	}

	private TeamEntity convertToModel(CreateTeamRequest createTeamRequest) {
		TeamEntity teamEntity = new TeamEntity();
		teamEntity.setTeamName(createTeamRequest.getTeamName());
		teamEntity.getDeveloperEntityList().addAll(createTeamRequest.getDevelopers().stream().map(developerRequest -> new DeveloperEntity(developerRequest.getName(), developerRequest.getPhoneNumber(), teamEntity)).collect(Collectors.toList()));
		return teamEntity;
	}


	public SMSServiceResponse sendAlert(String teamId) {
		log.info("Sending alert for team :::: {}", teamId);
		//fetch developer list for team id
		List<DeveloperEntity> developerEntityList = developerRepository.findByTeamId(UUID.fromString(teamId));

		if (developerEntityList.isEmpty()) {
			throw new NoDataFoundException(teamId);
		}

		log.info("DeveloperEntity list retrieved is  :::: {}", developerEntityList.size());

		// get any random developer
		DeveloperEntity randomDeveloperEntity = getRandomElement(developerEntityList, developerEntityList.size());

		log.info("Sending sms alert to developer :::: {}", randomDeveloperEntity.getName());
		//call sms service
		return invokeSMSService.sendSMS(randomDeveloperEntity.getPhoneNumber());
	}

	private DeveloperEntity getRandomElement(List<DeveloperEntity> list,
											 int bound){
		return list.get(
				ThreadLocalRandom.current().nextInt(list.size())
						% bound);
	}
}
