package com.leaderboard.dao.entity;

import com.leaderboard.dataStructure.LinkList;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@Data
public class Board extends BaseEntity {
    private String name;

    private int minScore;
    private int maxScore;

    private int minTillNow = 0;
    private int maxTillNow = 0;

    private Map<Integer, Integer> userScoreMap = new HashMap<Integer, Integer>();
    private Map<Integer, LinkList<Integer>> scoreListMap = new HashMap<Integer, LinkList<Integer>>();
}
