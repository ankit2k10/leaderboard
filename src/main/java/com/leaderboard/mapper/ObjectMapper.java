package com.leaderboard.mapper;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public interface ObjectMapper<Source, Target> {
    public void from(Source source, Target target);
}
