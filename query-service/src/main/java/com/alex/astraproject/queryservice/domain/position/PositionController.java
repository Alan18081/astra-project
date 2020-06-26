package com.alex.astraproject.queryservice.domain.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public Flux<PositionEntity> findMany() {
        return positionService.findMany();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<PositionEntity>> findOneById(@PathVariable String id) {
        return positionService.findById(id)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<PositionEntity>> findOneByName(@PathVariable String name) {
        return positionService.findOneByName(name)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
