package com.alex.astraproject.shared.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEvent {
    private String id;

    private String companyId;

    private String type;

    private Map<String, Object> data;

    private long revision;

    @Override
    public String toString() {
        return "CompanyEvent{" +
                "id='" + id + '\'' +
                ", companyId=" + companyId +
                ", type='" + type + '\'' +
                ", data=" + data +
                ", revision=" + revision +
                '}';
    }
}

