package com.alex.astraproject.queryservice.domain.sprint;

import com.alex.astraproject.queryservice.shared.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintEntity extends BaseEntity {

}
