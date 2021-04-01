package com.decathlon.alert.service;

import com.decathlon.alert.dto.CreateTeamRequest;
import com.decathlon.alert.dto.CreateTeamResponse;
import com.decathlon.alert.dto.DeveloperRequest;
import com.decathlon.alert.dto.SMSServiceResponse;
import com.decathlon.alert.exception.NoDataFoundException;
import com.decathlon.alert.exception.TeamAlreadyExistsException;
import com.decathlon.alert.model.DeveloperEntity;
import com.decathlon.alert.model.TeamEntity;
import com.decathlon.alert.repository.DeveloperRepository;
import com.decathlon.alert.repository.TeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

	public static final String MOCK_SUCCESS_MESSAGE = "Mock_Success_Message";

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private InvokeSMSService invokeSMSService;

	@Mock
	private DeveloperRepository developerRepository;

	@InjectMocks
	private AlertService alertService;

	@Test
	void test_create_Team() {
		CreateTeamRequest createTeamRequest = new CreateTeamRequest();
		createTeamRequest.setTeamName("Mock_Team_Name");
		DeveloperRequest developer = new DeveloperRequest();
		developer.setName("Mock_developer");
		developer.setPhoneNumber("Mock_Phone_Number");
		createTeamRequest.getDevelopers().add(developer);
		TeamEntity teamEntity = new TeamEntity();
		UUID id = UUID.randomUUID();
		teamEntity.setId(id);

		Mockito.lenient().when(teamRepository.findByTeamName(any())).thenReturn(Optional.empty());
		Mockito.lenient().when(teamRepository.save(any())).thenReturn(teamEntity);

		CreateTeamResponse createTeamResponse = alertService.createTeam(createTeamRequest);

		Assert.assertEquals(id.toString(), createTeamResponse.getTeamId());
		verify(teamRepository,  times(1)).save(any());
	}

	@Test
	void test_send_Alert() {
		TeamEntity teamEntity = new TeamEntity();
		UUID id = UUID.randomUUID();
		teamEntity.setId(id);
		teamEntity.setTeamName("Mock_Team");

		DeveloperEntity developerEntity = new DeveloperEntity();
		developerEntity.setPhoneNumber("Mock_Phone_Number");
		developerEntity.setName("Mock_Name");
		developerEntity.setTeam(teamEntity);

		List<DeveloperEntity> developerEntityList = new ArrayList<>();
		developerEntityList.add(developerEntity);

		SMSServiceResponse smsServiceResponse = new SMSServiceResponse();
		smsServiceResponse.setSuccess(MOCK_SUCCESS_MESSAGE);

		Mockito.lenient().when(developerRepository.findByTeamId(any())).thenReturn(developerEntityList);
		Mockito.lenient().when(invokeSMSService.sendSMS(any())).thenReturn(smsServiceResponse);

		SMSServiceResponse smsServiceResponseActual = alertService.sendAlert(teamEntity.getId().toString());

		Assert.assertEquals(MOCK_SUCCESS_MESSAGE, smsServiceResponseActual.getSuccess());
		verify(developerRepository,  times(1)).findByTeamId(any());
		verify(invokeSMSService,  times(1)).sendSMS(any());
	}

	@Test
	void test_send_Alert_fail_if_team_not_found() {
		TeamEntity teamEntity = new TeamEntity();
		UUID id = UUID.randomUUID();
		teamEntity.setId(id);
		teamEntity.setTeamName("Mock_Team");

		DeveloperEntity developerEntity = new DeveloperEntity();
		developerEntity.setPhoneNumber("Mock_Phone_Number");
		developerEntity.setName("Mock_Name");
		developerEntity.setTeam(teamEntity);

		List<DeveloperEntity> developerEntityList = new ArrayList<>();

		SMSServiceResponse smsServiceResponse = new SMSServiceResponse();
		smsServiceResponse.setSuccess(MOCK_SUCCESS_MESSAGE);

		Mockito.lenient().when(developerRepository.findByTeamId(any())).thenReturn(developerEntityList);
		Mockito.lenient().when(invokeSMSService.sendSMS(any())).thenReturn(smsServiceResponse);

		Assertions.assertThrows(NoDataFoundException.class, () -> {
			alertService.sendAlert(teamEntity.getId().toString());
		});
	}

	@Test
	void test_create_Team_throw_error_if_team_already_present() {
		CreateTeamRequest createTeamRequest = new CreateTeamRequest();
		createTeamRequest.setTeamName("Mock_Team_Name");
		DeveloperRequest developer = new DeveloperRequest();
		developer.setName("Mock_developer");
		developer.setPhoneNumber("Mock_Phone_Number");
		createTeamRequest.getDevelopers().add(developer);
		TeamEntity teamEntity = new TeamEntity();
		UUID id = UUID.randomUUID();
		teamEntity.setId(id);

		Mockito.lenient().when(teamRepository.save(any())).thenReturn(teamEntity);
		Mockito.lenient().when(teamRepository.findByTeamName(any())).thenReturn(Optional.of(teamEntity));

		Assertions.assertThrows(TeamAlreadyExistsException.class, () -> {
			alertService.createTeam(createTeamRequest);
		});
	}
}
