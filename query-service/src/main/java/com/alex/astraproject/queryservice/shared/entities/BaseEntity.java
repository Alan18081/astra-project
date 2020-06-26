package com.alex.astraproject.queryservice.shared.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

	@Id
	protected String id;

	@Property
	protected Date createdAt;

	@Property
	protected Date deletedAt;

	@Property
	protected long revision;

}
