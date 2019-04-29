package com.alex.astraproject.queryservice.domain.company;

import com.alex.astraproject.shared.Aggregate;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.events.CompanyEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity implements Aggregate {

    @Id
    private UUID id;

    @Property
    private String name;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private long numberOfEmployees;

    @Property
    private long deletedAt;

    @Property
    private int revision;

    @Override
    public void initialize() {
        this.id = UUID.randomUUID();
    }

    public void replay(List<CompanyEvent> events) {
        events.forEach(this::applyEvent);
    }

    public void applyEvent(CompanyEvent event) {
        switch (event.getType()) {
            case CompanyEventType.CREATED: {
                this.name = (String) event.getData().get("name");
                this.email = (String) event.getData().get("email");
                this.password = (String) event.getData().get("password");

                break;
            }
            case CompanyEventType.UPDATED: {
                try {
                    BeanUtils.populate(this, event.getData());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
            case CompanyEventType.DELETED: {
                this.deletedAt = (Long) event.getData().get("deletedAt");
            }
        }
    }
}
