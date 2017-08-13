package com.leaderboard.service.impl;

import com.google.inject.Inject;
import com.leaderboard.dao.BoardDao;
import com.leaderboard.dao.entity.Board;
import com.leaderboard.service.LeaderBoardService;

import java.util.List;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
public class LeaderBoardServiceImpl implements LeaderBoardService {
    @Inject
    private BoardDao boardDao;

    @Override
    public List<Integer> top(Integer boardId, int n) {
        return boardDao.top(boardId, n);
    }

    // Not using timestamp. Assuming all updates are in monotonically increasing time.
    // Current implementation is based on First come first serve basis.
    @Override
    public void updateScore(Integer boardId, Integer userId, int delta, int timeStamp) {
        boardDao.updateScore(boardId, userId, delta);
    }

    @Override
    public List<Integer> relative(Integer boardId, Integer userId, int n1, int n2) {
        return boardDao.relative(boardId, userId, n1, n2);
    }

    @Override
    public Integer saveEntity(Board entity) {
        return boardDao.save(entity);
    }

    @Override
    public Board fetchEntity(Integer entityId) {
        return boardDao.findOne(entityId);
    }
}
