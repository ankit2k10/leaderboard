package com.leaderboard.dao.exceptions;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class UserNotExistsException extends EntityNotExistsException {

    public UserNotExistsException(String msg) {
        super(msg);
    }
}
