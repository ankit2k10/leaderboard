package com.leaderboard.exceptions;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
public class ServerException extends RuntimeException {
    public ServerException(String msg) {
        super(msg);
    }
}
