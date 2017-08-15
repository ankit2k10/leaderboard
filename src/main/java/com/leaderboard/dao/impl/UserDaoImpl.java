package com.leaderboard.dao.impl;

import com.leaderboard.dao.UserDao;
import com.leaderboard.dao.entity.User;
import com.leaderboard.dao.exceptions.DuplicateEntityException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class UserDaoImpl implements UserDao {
    private AtomicInteger idStore = new AtomicInteger(0);
    private Map<Integer, User> entityMap = new HashMap<Integer, User>();
    private Set<String> emailIds = new HashSet<String>();

    @Override
    public synchronized Integer save(User entity) {
        if(emailIds.contains(entity.getEmailId())) {
            throw new DuplicateEntityException("Duplicate email id, user exists");
        }

        int id = idStore.addAndGet(1);
        entity.setId(id);
        entityMap.put(id, entity);
        emailIds.add(entity.getEmailId());
        return id;
    }

    @Override
    public User findOne(Integer id) {
        return entityMap.get(id);
    }
}
