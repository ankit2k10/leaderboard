package com.leaderboard.dao;

import com.leaderboard.dao.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public interface BaseDao<T extends BaseEntity, ID extends Serializable> {
    ID save(T entity);

    T findOne(ID id);
}
