package com.leaderboard.dao;

import com.leaderboard.dao.entity.Board;

import java.util.List;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public interface BoardDao extends BaseDao<Board, Integer> {
    public void updateScore(Integer boardId, Integer userId, int delta);

    public List<Integer> top(Integer boardId, int n);

    public List<Integer> relative(Integer boardId, Integer userId, int n1, int n2);
}
