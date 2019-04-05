package com.alex.astraproject.companiesservice.domain.position;

import com.alex.astraproject.companiesservice.domain.position.PositionEntity;
import com.alex.astraproject.shared.dto.positions.CreatePositionDto;
import com.alex.astraproject.shared.dto.positions.FindManyPositionsDto;
import com.alex.astraproject.shared.dto.positions.UpdatePositionDto;
import com.alex.astraproject.shared.responses.PaginatedResponse;

public interface PositionsService {

    PaginatedResponse<PositionEntity> findManyByCompany(FindManyPositionsDto dto);

    PositionEntity createOne(CreatePositionDto dto);

    PositionEntity findById(long id);

    PositionEntity updateById(long id, UpdatePositionDto dto);

    void removeById(long id);

}
