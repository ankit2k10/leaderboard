package com.leaderboard.dao.exceptions;

import com.leaderboard.exceptions.ClientException;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
public class DuplicateEntityException extends ClientException {
    public DuplicateEntityException(String msg) {
        super(msg);
    }
}
