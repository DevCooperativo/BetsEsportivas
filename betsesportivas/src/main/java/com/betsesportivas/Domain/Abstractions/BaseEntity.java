package com.betsesportivas.Domain.Abstractions;

public abstract class BaseEntity {
    private final int _id;

    public BaseEntity(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }
}
