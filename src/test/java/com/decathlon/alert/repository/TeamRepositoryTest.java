package com.decathlon.alert.repository;

import com.decathlon.alert.model.DeveloperEntity;
import com.decathlon.alert.model.TeamEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class TeamRepositoryTest {

	private static final String MOCK_TEAM_NAME = "Mock_Team_Name";
	private static final String MOCK_DEVELOPER = "Mock_Developer";
	private static final String MOCK_PHONE_NUMBER = "Mock_Phone_Number";

	@Autowired
	private TeamRepository repository;

	@Test
	public void testRepository(){
		TeamEntity teamEntity = getTeamEntity();

		List<DeveloperEntity> developerEntityList = getDeveloperEntities(teamEntity);

		teamEntity.getDeveloperEntityList().addAll(developerEntityList);

		teamEntity = repository.save(teamEntity);

		Assert.assertNotNull(teamEntity.getId());
		Assert.assertEquals(MOCK_TEAM_NAME, teamEntity.getTeamName());
	}

	private List<DeveloperEntity> getDeveloperEntities(TeamEntity teamEntity) {
		List<DeveloperEntity> developerEntityList = new ArrayList<>();
		DeveloperEntity developerEntity = new DeveloperEntity();
		developerEntity.setName(MOCK_DEVELOPER);
		developerEntity.setPhoneNumber(MOCK_PHONE_NUMBER);
		developerEntity.setTeam(teamEntity);

		developerEntityList.add(developerEntity);
		return developerEntityList;
	}

	private TeamEntity getTeamEntity() {
		TeamEntity teamEntity = new TeamEntity();
		teamEntity.setTeamName(MOCK_TEAM_NAME);
		return teamEntity;
	}
}
