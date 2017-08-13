package com.leaderboard.dao.exceptions;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class BoardNotExistsException extends EntityNotExistsException {
    public BoardNotExistsException(String msg) {
        super(msg);
    }
}
