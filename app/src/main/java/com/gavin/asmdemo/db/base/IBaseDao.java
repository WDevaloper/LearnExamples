package com.gavin.asmdemo.db.base;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author wfy
 */
public interface IBaseDao<T> {
    long insert(@NonNull T entity);

    long insert(@NonNull List<T> entitys);

    long update(@NonNull T entity, @NonNull T where);

    long delete(@NonNull T where);

    List<T> query(@NonNull T where);

    List<T> query(@NonNull T where, String orderBy, Integer startIndex, Integer limit);
}
