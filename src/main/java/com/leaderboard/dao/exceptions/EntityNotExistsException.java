package com.leaderboard.dao.exceptions;

import com.leaderboard.exceptions.ClientException;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class EntityNotExistsException extends ClientException {

    public EntityNotExistsException(String msg) {
        super(msg);
    }
}
