package com.example.demo.util;

import com.example.demo.entity.dao.DaoEntity;
import com.example.demo.repo.AbstractRepo;

public class RepoFiller<E extends DaoEntity, R extends AbstractRepo<E>> {

    public void addToRepository(R repo, E entity) {
        repo.fill(entity);
    }
}
