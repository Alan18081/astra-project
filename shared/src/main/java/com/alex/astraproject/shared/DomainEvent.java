package com.alex.astraproject.shared;

public class DomainEvent<T, ID> {
    private ID id;
    private T subject;
}
