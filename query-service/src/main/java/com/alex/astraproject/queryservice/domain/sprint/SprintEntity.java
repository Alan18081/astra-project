package com.alex.astraproject.queryservice.domain.sprint;

import com.alex.astraproject.queryservice.shared.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintEntity extends BaseEntity {

	@Property
	private String name;

	@Property
	private String description;

	@Property
	private Date completeAt;

}
