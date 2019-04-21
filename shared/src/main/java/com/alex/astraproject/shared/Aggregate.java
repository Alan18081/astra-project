package com.alex.astraproject.shared;

import java.util.List;
import java.util.UUID;

public interface Aggregate {

    void initialize(UUID id);

//    void replay(List<? extends DomainEvent<T>> events);
}
