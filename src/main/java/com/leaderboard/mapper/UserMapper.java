package com.leaderboard.mapper;

import com.leaderboard.dao.entity.User;
import com.leaderboard.dto.user.UserCreateDto;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class UserMapper implements ObjectMapper<UserCreateDto, User> {
    @Override
    public void from(UserCreateDto userCreateDto, User user) {
        user.setName(userCreateDto.getName());
        user.setEmailId(userCreateDto.getEmailId());
    }
}
