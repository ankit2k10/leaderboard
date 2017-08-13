package com.leaderboard.service;

import com.google.inject.ImplementedBy;
import com.leaderboard.dao.entity.Board;
import com.leaderboard.service.impl.LeaderBoardServiceImpl;

import java.util.List;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
@ImplementedBy(LeaderBoardServiceImpl.class)
public interface LeaderBoardService extends BaseEntityService<Board, Integer> {
    /*
        This will return top n users of leader board
     */
    public List<Integer> top(Integer boardId, int n);

    public void updateScore(Integer boardId, Integer userId, int delta, int timeStamp);

     /*
        Return users where first N1 users are just above user_id,
        N1+1th user is user_id and last N2 users are just below user_id.
        In case there are lesser number of users, list.size() < n1+n2+1
     */
    public List<Integer> relative(Integer boardId, Integer userId, int n1, int n2);
}
