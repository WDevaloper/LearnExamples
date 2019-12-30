package com.gavin.asmdemo.db;

import java.util.List;

/**
 * @author wfy
 */
public interface IBaseDao<T extends BaseModel> {
    long insert(T entity);

    long update(T where);

    long delete(T where);

    List<T> query(T where);

    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);
}
