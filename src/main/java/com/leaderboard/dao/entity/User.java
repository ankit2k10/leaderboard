package com.leaderboard.dao.entity;

import lombok.Data;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@Data
public class User extends BaseEntity {
    private String name;
    private String emailId;
}
