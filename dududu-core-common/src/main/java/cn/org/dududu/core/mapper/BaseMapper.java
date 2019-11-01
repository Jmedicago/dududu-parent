package cn.org.dududu.core.mapper;

import cn.org.dududu.core.query.base.BaseQuery;

import java.util.List;

public interface BaseMapper<T> {
    void save(T t);

    void savePart(T t);

    void delete(long id);

    void update(T t);

    void updatePart(T t);

    T get(long id);

    int queryTotal(BaseQuery query);

    List<T> query(BaseQuery query);
}
