package com.alex.astraproject.queryservice.domain.employee;

import com.alex.astraproject.queryservice.shared.entities.BaseEntity;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.events.EmployeeEvent;
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
public class EmployeeEntity extends BaseEntity {

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private BigDecimal salary;

    @Relationship(type = "WORKS_IN", direction = Relationship.INCOMING)
    private Set<EmployeeEntity> employeeEntitySet;

    @Builder
    public EmployeeEntity(
      String id, Date createdAt, Date deletedAt, long revision,
      String firstName, String lastName, String email, String password, BigDecimal salary
    ) {
        super(id, createdAt, deletedAt, revision);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salary = salary;
    }

    public void replay(List<EmployeeEvent> events) {
        events.forEach(this::applyEvent);
    }

    public void applyEvent(EmployeeEvent event) {
        switch (event.getType()) {
            case EmployeeEventType.CREATED: {
                System.out.println(event.getEmployeeId());
                this.id = event.getEmployeeId();
                this.firstName = (String) event.getData().get("firstName");
                this.lastName = (String) event.getData().get("firstName");
                this.email = (String) event.getData().get("email");
                this.password = (String) event.getData().get("password");
                this.createdAt = (Date) event.getData().get("createdAt");
                break;
            }
            case EmployeeEventType.UPDATED: {
                try {
                    BeanUtils.populate(this, event.getData());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
            case EmployeeEventType.FIRED: {
                this.deletedAt = (Date) event.getData().get("deletedAt");
            }
        }
    }
}
