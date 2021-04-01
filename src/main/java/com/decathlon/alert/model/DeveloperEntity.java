package com.decathlon.alert.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "developers")
@Data
public class DeveloperEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Type(type="uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	private String name;

	private String phoneNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "team_id", nullable = false)
	private TeamEntity team;

	public DeveloperEntity() {
	}

	public DeveloperEntity(String name, String phoneNumber, TeamEntity team) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.team = team;
	}
}
