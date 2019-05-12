package com.alex.astraproject.queryservice.domain.project;

import com.alex.astraproject.queryservice.domain.employee.EmployeeEntity;
import com.alex.astraproject.queryservice.domain.position.PositionEntity;
import com.alex.astraproject.queryservice.shared.entities.BaseEntity;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.entities.Position;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.events.ProjectEvent;
import com.alex.astraproject.shared.statuses.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity extends BaseEntity {

    @Property
    private String name;

    @Property
    private String description;

    @Relationship(type = "WORK_ON", direction = Relationship.INCOMING)
    private Set<EmployeeEntity> employees;

    @Property
    private ProjectStatus status;

    @Property
    private String companyId;

    @Relationship(type = "HAVE_POSITIONS", direction = Relationship.INCOMING)
    private Set<PositionEntity> positions;

    @Builder
    public ProjectEntity(
      String id, Date createdAt, Date deletedAt, long revision,
      String name, String description, ProjectStatus status, String companyId
    ) {
        super(id, createdAt, deletedAt, revision);
        this.name = name;
        this.description = description;
        this.status = status;
        this.companyId = companyId;
    }

    public void replay(List<ProjectEvent> events) {
        events.forEach(this::applyEvent);
    }

    public void applyEvent(ProjectEvent event) {
        switch (event.getType()) {
            case ProjectEventType.CREATED: {
                this.id = event.getProjectId();
                this.createdAt = (Date) event.getData().get("createdAt");
                break;
            }
            case ProjectEventType.ADDED_EMPLOYEE: {
                this.employees.add((EmployeeEntity) event.getData().get("position"));
            }
            case ProjectEventType.REMOVED_EMPLOYEE: {
                EmployeeEntity employeeEntity = (EmployeeEntity) event.getData().get("position");
                this.employees.remove(employeeEntity);
            }
            case ProjectEventType.ADDED_POSITION: {
                this.positions.add((PositionEntity) event.getData().get("position"));
            }
            case ProjectEventType.REMOVED_POSITION: {
                PositionEntity positionEntity = (PositionEntity) event.getData().get("position");
                this.positions.remove(positionEntity);
            }
            case ProjectEventType.UPDATED: {
                try {
                    BeanUtils.populate(this, event.getData());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
            case ProjectEventType.DELETED: {
                this.deletedAt = (Date) event.getData().get("deletedAt");
            }
        }
    }
}
