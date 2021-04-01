package com.decathlon.alert.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teams")
@Data
public class TeamEntity {

	@Id
	@Type(type="uuid-char")
	@GeneratedValue(generator = "UUIDConstraint")
	@GenericGenerator(
			name = "UUIDConstraint",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	private String teamName;

	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private List<DeveloperEntity> developerEntityList = new ArrayList<>();
}


