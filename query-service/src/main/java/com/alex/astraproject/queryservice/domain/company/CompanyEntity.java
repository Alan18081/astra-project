package com.alex.astraproject.queryservice.domain.company;

import com.alex.astraproject.queryservice.shared.entities.BaseEntity;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.events.CompanyEvent;
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

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity extends BaseEntity {

    @Property
    private String name;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private long numberOfEmployees;

    @Property
    private String companyId;

    @Relationship(type = "WORK_IN")
    private CompanyEntity company;

    @Builder
    public CompanyEntity(
      String id, Date createdAt, Date deletedAt, long revision,
      String name, String email, String password, long numberOfEmployees, String companyId, CompanyEntity company
    ) {
        super(id, createdAt, deletedAt, revision);
        this.name = name;
        this.email = email;
        this.password = password;
        this.numberOfEmployees = numberOfEmployees;
        this.companyId = companyId;
        this.company = company;
    }

    public void replay(List<CompanyEvent> events) {
        events.forEach(this::applyEvent);
    }

    public void applyEvent(CompanyEvent event) {
        switch (event.getType()) {
            case CompanyEventType.CREATED: {
                this.id = event.getCompanyId();
                this.name = (String) event.getData().get("name");
                this.email = (String) event.getData().get("email");
                this.password = (String) event.getData().get("password");
                this.revision = event.getRevision();
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

                this.revision = event.getRevision();
                break;
            }
            case CompanyEventType.DELETED: {
                this.deletedAt = (Date) event.getData().get("deletedAt");
                this.revision = event.getRevision();
            }
        }
    }
}
