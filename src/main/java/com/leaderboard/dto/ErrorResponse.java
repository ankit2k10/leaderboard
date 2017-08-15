package com.leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private int code;
}
