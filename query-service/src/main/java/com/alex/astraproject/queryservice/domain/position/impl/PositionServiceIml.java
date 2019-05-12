package com.alex.astraproject.queryservice.domain.position.impl;

import com.alex.astraproject.queryservice.domain.position.PositionEntity;
import com.alex.astraproject.queryservice.domain.position.PositionRepository;
import com.alex.astraproject.queryservice.domain.position.PositionService;
import com.alex.astraproject.shared.events.PositionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PositionServiceIml implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public Flux<PositionEntity> findMany() {
        return Flux.fromIterable(positionRepository.findAll());
    }

    @Override
    public void createOne(PositionEvent event) {
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.applyEvent(event);
        positionRepository.save(positionEntity);
    }

    @Override
    public void updateById(PositionEvent event) {
        Mono.justOrEmpty(positionRepository.findById(event.getPositionId()))
        .subscribe(positionEntity -> {
            positionEntity.applyEvent(event);
            positionRepository.save(positionEntity);
        });
    }

    @Override
    public Mono<PositionEntity> findById(String id) {
        return Mono.justOrEmpty(positionRepository.findById(id));
    }

    @Override
    public Mono<PositionEntity> findOneByName(String name) {
        return Mono.justOrEmpty(positionRepository.findFirstByName(name));
    }

}
