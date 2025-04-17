package com.betsesportivas.Domain.Abstractions;

public abstract class BaseEntity {
    private Integer _id;

    public BaseEntity(Integer id) {
        _id = id;
    }

    public Integer GetId() {
        return _id;
    }
}
