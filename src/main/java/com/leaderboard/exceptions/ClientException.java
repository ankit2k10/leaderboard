package com.leaderboard.exceptions;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
public class ClientException extends RuntimeException {
    public ClientException(String msg) {
        super(msg);
    }
}
