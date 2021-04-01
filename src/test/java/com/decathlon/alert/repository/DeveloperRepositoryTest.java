package com.decathlon.alert.repository;

import com.decathlon.alert.model.DeveloperEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

@Sql({"/insert_team.sql"})
@DataJpaTest
public class DeveloperRepositoryTest {

	@Autowired
	private DeveloperRepository repository;

	@Test
	public void testRepository() {
        List<DeveloperEntity> developerEntityList = repository.findByTeamId(UUID.fromString("11eb2f22-3b50-3d80-b196-54e1ad12b6a9"));
		Assert.assertEquals(1, developerEntityList.size());
	}
}