package com.leaderboard.dao.exceptions;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class EntityNotExistsException extends RuntimeException {

    public EntityNotExistsException(String msg) {
        super(msg);
    }
}
