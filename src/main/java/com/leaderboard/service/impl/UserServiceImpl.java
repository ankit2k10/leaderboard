package com.leaderboard.service.impl;

import com.google.inject.Inject;
import com.leaderboard.dao.UserDao;
import com.leaderboard.dao.entity.User;
import com.leaderboard.service.UserService;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public Integer saveEntity(User entity) {
        return userDao.save(entity);
    }

    @Override
    public User fetchEntity(Integer entityId) {
        return userDao.findOne(entityId);
    }
}
