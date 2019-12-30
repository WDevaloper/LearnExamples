package com.gavin.asmdemo.db;

public interface IBaseDao<T> {
    long insert(T entity);
}
