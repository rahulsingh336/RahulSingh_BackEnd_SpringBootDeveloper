package com.decathlon.alert.repository;

import com.decathlon.alert.model.TeamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends CrudRepository<TeamEntity, UUID> {

	Optional<TeamEntity> findByTeamName(String teamName);

}
