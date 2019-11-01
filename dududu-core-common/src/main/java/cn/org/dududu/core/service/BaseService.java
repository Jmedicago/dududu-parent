package cn.org.dududu.core.service;

import cn.org.dududu.core.query.base.BaseQuery;
import cn.org.dududu.core.utils.Page;

import java.util.List;

public interface BaseService<T> {
    void save(T t);

    void savePart(T t);

    void delete(long id);

    void update(T t);

    void updatePart(T t);

    T get(long id);

    int queryTotal(BaseQuery query);

    List<T> query(BaseQuery query);

    Page<T> queryPage(BaseQuery query);
}
