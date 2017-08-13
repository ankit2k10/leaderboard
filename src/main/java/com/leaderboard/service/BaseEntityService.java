package com.leaderboard.service;

import com.leaderboard.dao.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public interface BaseEntityService<T extends BaseEntity, ID extends Serializable> {
    ID saveEntity(T entity) ;

    T fetchEntity(ID entityId);
}
