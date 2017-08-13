package com.leaderboard.dao.impl;

import com.google.inject.Inject;
import com.leaderboard.dao.BoardDao;
import com.leaderboard.dao.UserDao;
import com.leaderboard.dao.entity.Board;
import com.leaderboard.dataStructure.DoublyLinkList;
import com.leaderboard.dataStructure.LinkList;
import com.leaderboard.dao.exceptions.BoardNotExistsException;
import com.leaderboard.dao.exceptions.UserNotExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class BoardDaoImpl implements BoardDao {
    private AtomicInteger idStore = new AtomicInteger(0);
    private Map<Integer, Board> entityMap = new HashMap<Integer, Board>();
    private Map<Integer, Object> lock1 = new HashMap<Integer, Object>();
    private Map<Integer, Object> lock2 = new HashMap<Integer, Object>();

    @Inject
    private UserDao userDao;

    @Override
    public synchronized Integer save(Board entity) {
        int id = idStore.addAndGet(1);
        entity.setId(id);
        entityMap.put(id, entity);
        lock1.put(id, new Object());
        lock2.put(id, new Object());
        return id;
    }

    @Override
    public Board findOne(Integer id) {
        return entityMap.get(id);
    }

    @Override
    public void updateScore(Integer boardId, Integer userId, int newScore) {
        Board board = validateId(boardId);

        if(userDao.findOne(userId) == null) {
            throw new UserNotExistsException("Invalid user id");
        }

        // If user exists in leader board, remove it
        remove(board, userId);

        add(board, userId, newScore);
    }

    @Override
    public List<Integer> top(Integer boardId, int n) {
        return top(validateId(boardId), n);
    }

    @Override
    public List<Integer> relative(Integer boardId, Integer userId, int n1, int n2) {
        return relative(boardId, userId, n1, n1);
    }

    private Board validateId(Integer boardId) {
        Board board = entityMap.get(boardId);
        if(board == null) {
            throw new BoardNotExistsException("Leaderboard does not exists");
        }
        return board;
    }

    private Integer getScore(Board board, Integer userId) {
        return board.getUserScoreMap().get(userId);
    }

    private void add(Board board, Integer userId, int score) {
        LinkList<Integer> linkList = board.getScoreListMap().get(score);
        if(linkList != null) {
            linkList.addLast(userId);
        }
        else {
            // Taking lock to avoid loss of update
            synchronized (lock1.get(board.getId())) {
                linkList = new DoublyLinkList<Integer>();
                linkList.addFirst(userId);
                board.getScoreListMap().put(score, linkList);
            }
            updateAddition(board, score);
        }

        board.getUserScoreMap().put(userId, score);
    }

    private void remove(Board board, Integer userId) {
        Integer score = board.getUserScoreMap().get(userId);
        if(score == null) return;
        LinkList<Integer> linkList = board.getScoreListMap().get(score);
        if(linkList!=null) {
            linkList.remove(userId);
            if(linkList.head() == null) {
                updateRemoval(board, score);
            }
            board.getUserScoreMap().remove(userId);
        }
    }

    private void updateAddition(Board board, int score) {
        synchronized (lock2.get(board.getId())) {
            if(score > board.getMaxTillNow())
                board.setMaxTillNow(score);

            if(score < board.getMinTillNow())
                board.setMinTillNow(score);
        }
    }

    private void updateRemoval(Board board, int score) {
        synchronized (lock2.get(board.getId())) {
            // Ideally I should iterate map here till non-empty linklist found out
            if(score == board.getMaxTillNow())
                board.setMaxTillNow(score-1);

            if(score == board.getMinTillNow())
                board.setMaxTillNow(score+1);
        }
    }

    private List<Integer> top(Board board, int n) {
        int i=0;
        List<Integer> ans = new ArrayList<Integer>();

        // Not taking any lock, as this is read and if any update comes at same time, then it will be added to final output
        for(int j=board.getMaxTillNow(); j>=board.getMinTillNow() && i<n; j--) {
            if(board.getScoreListMap().containsKey(j)) {
                LinkList linkList = board.getScoreListMap().get(j);
                DoublyLinkList.Node<Integer> head = linkList.head();
                while (head != null) {
                    ans.add(head.get());
                    head = head.getNext();
                    i++;
                }
            }
        }

        return ans;
    }


    private List<Integer> relative(Board board, Integer userId, int n1, int n2) {
        List<Integer> ans = new ArrayList<Integer>();

        Integer score = board.getUserScoreMap().get(userId);
        if(score != null) {
            LinkList<Integer> linkList = board.getScoreListMap().get(score);

            DoublyLinkList.Node node = linkList.find(userId);

            ans.addAll(upper(board, score, node.getNext(), n1));

            ans.add(userId);

            ans.addAll(lower(board, score, node.getPrev(), n2));

        }

        return null;
    }

    private List<Integer> upper(Board board, int score, DoublyLinkList.Node<Integer> head, int n) {
        List<Integer> ans = new ArrayList<Integer>();
        int i=0;

        while(i<n && head!=null) {
            ans.add(head.get());
            head = head.getNext();
            i++;
        }

        int j=score+1;
        while(i<n && j<=board.getMaxTillNow()) {
            LinkList<Integer> linkList = board.getScoreListMap().get(j);
            if(linkList!=null) {
                head = linkList.head();
                while(i<n && head!=null) {
                    ans.add(head.get());
                    i++;
                }
            }
            j++;
        }

        return ans;
    }

    private List<Integer> lower(Board board, int score, DoublyLinkList.Node<Integer> tail, int n) {
        List<Integer> ans = new ArrayList<Integer>();
        int i=0;

        while(i<n && tail!=null) {
            ans.add(tail.get());
            tail = tail.getPrev();
            i++;
        }

        int j=score-1;
        while(i<n && j>=board.getMinTillNow()) {
            LinkList<Integer> linkList = board.getScoreListMap().get(j);
            if(linkList!=null) {
                tail = linkList.tail();
                while(i<n && tail!=null) {
                    ans.add(tail.get());
                    i--;
                }
            }
            j--;
        }

        return ans;
    }
}
