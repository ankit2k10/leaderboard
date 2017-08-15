package com.leaderboard;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.leaderboard.dao.BoardDao;
import com.leaderboard.dao.UserDao;
import com.leaderboard.dao.impl.BoardDaoImpl;
import com.leaderboard.dao.impl.UserDaoImpl;
import com.leaderboard.mapper.BoardMapper;
import com.leaderboard.mapper.ObjectMapper;
import com.leaderboard.mapper.UserMapper;
import com.leaderboard.mapper.annotations.MyBoardMapper;
import com.leaderboard.mapper.annotations.MyUserMapper;
import com.leaderboard.service.LeaderBoardService;
import com.leaderboard.service.UserService;
import com.leaderboard.service.impl.LeaderBoardServiceImpl;
import com.leaderboard.service.impl.UserServiceImpl;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDao.class).to(UserDaoImpl.class).in(Singleton.class);
        bind(BoardDao.class).to(BoardDaoImpl.class).in(Singleton.class);

        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(LeaderBoardService.class).to(LeaderBoardServiceImpl.class).in(Singleton.class);

        bind(ObjectMapper.class).annotatedWith(MyUserMapper.class).to(UserMapper.class).in(Singleton.class);
        bind(ObjectMapper.class).annotatedWith(MyBoardMapper.class).to(BoardMapper.class).in(Singleton.class);
    }
}
