package com.example.demo.repo;

import com.example.demo.entity.dao.DaoEntity;

public interface AbstractRepo<T extends DaoEntity> {

    void fill(T entity);
}
