package com.decathlon.alert.repository;

import com.decathlon.alert.model.DeveloperEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface DeveloperRepository extends CrudRepository<DeveloperEntity, UUID> {

	List<DeveloperEntity> findByTeamId(UUID teamId);
}
