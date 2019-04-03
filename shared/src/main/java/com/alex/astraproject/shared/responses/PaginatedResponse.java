package com.alex.astraproject.shared.responses;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private int page;
    private int limit;
    private long totalCount;

    public PaginatedResponse(List<T> data, int page, int limit, long totalCount) {
        this.data = data;
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
    }
}
