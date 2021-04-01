package com.decathlon.alert.controller;

import com.decathlon.alert.dto.CreateTeamRequest;
import com.decathlon.alert.dto.CreateTeamResponse;
import com.decathlon.alert.dto.DeveloperRequest;
import com.decathlon.alert.dto.SMSServiceResponse;
import com.decathlon.alert.service.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AlertController.class)
public class AlertControllerTest {

	private static final String MOCK_TEAM_NAME = "Mock_Team_Name";
	private static final String MOCK_NAME = "Mock_Name";
	private static final String MOCK_PHONE_NUMBER = "Mock_Phone_Number";
	private static final String MOCK_TEAM_ID = "Mock_Team_Id";
	private static final String ALERT_SENT = "alert sent";
	public static final String INVALID_TEAM_ID = "Invalid_team_id";

	@MockBean
	private AlertService alertService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void should_create_new_Team() throws Exception {
		CreateTeamResponse createTeamResponse = getCreateTeamResponse();

		CreateTeamRequest createTeamRequest = getCreateTeamRequest();

		List<DeveloperRequest> developerRequests = getDeveloperRequests();

		createTeamRequest.getDevelopers().addAll(developerRequests);

		when(alertService.createTeam(any())).thenReturn(createTeamResponse);

		mvc.perform(MockMvcRequestBuilders
				.post("/team")
				.content(asJsonString(createTeamRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.teamId").exists());
	}

	@Test
	public void should_throw_error_with_zero_developers() throws Exception {
		CreateTeamResponse createTeamResponse = getCreateTeamResponse();

		CreateTeamRequest createTeamRequest = getCreateTeamRequest();

		when(alertService.createTeam(any())).thenReturn(createTeamResponse);

		mvc.perform(MockMvcRequestBuilders
				.post("/team")
				.content(asJsonString(createTeamRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists());
	}

	@Test
	public void should_send_alert() throws Exception {
		String team_id = UUID.randomUUID().toString();
		SMSServiceResponse createTeamResponse = new SMSServiceResponse();
		createTeamResponse.setSuccess(ALERT_SENT);

		when(alertService.sendAlert(any())).thenReturn(createTeamResponse);

		mvc.perform(MockMvcRequestBuilders
				.post("/" + team_id + "/alert")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").exists());
	}

	@Test
	public void should_fail_invalid_team_id() throws Exception {
		SMSServiceResponse createTeamResponse = new SMSServiceResponse();
		createTeamResponse.setSuccess(ALERT_SENT);

		when(alertService.sendAlert(any())).thenReturn(createTeamResponse);

		mvc.perform(MockMvcRequestBuilders
				.post("/" + INVALID_TEAM_ID + "/alert")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
	}

	private List<DeveloperRequest> getDeveloperRequests() {
		List<DeveloperRequest> developerRequests = new ArrayList<>();
		DeveloperRequest developerRequest = new DeveloperRequest();
		developerRequest.setName(MOCK_NAME);
		developerRequest.setPhoneNumber(MOCK_PHONE_NUMBER);
		developerRequests.add(developerRequest);
		return developerRequests;
	}

	private CreateTeamRequest getCreateTeamRequest() {
		CreateTeamRequest createTeamRequest = new CreateTeamRequest();
		createTeamRequest.setTeamName(MOCK_TEAM_NAME);
		return createTeamRequest;
	}

	private CreateTeamResponse getCreateTeamResponse() {
		CreateTeamResponse createTeamResponse = new CreateTeamResponse();
		createTeamResponse.setTeamId(MOCK_TEAM_ID);
		return createTeamResponse;
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

