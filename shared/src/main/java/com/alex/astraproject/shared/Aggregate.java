package com.alex.astraproject.shared;

import java.util.List;

public interface Aggregate<T> {

    void initialize();

//    void replay(List<? extends DomainEvent<T>> events);
}
