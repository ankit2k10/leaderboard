package com.leaderboard.mapper;

import com.leaderboard.dao.entity.Board;
import com.leaderboard.dto.board.BoardCreateDto;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
public class BoardMapper implements ObjectMapper<BoardCreateDto, Board> {
    @Override
    public void from(BoardCreateDto createDto, Board board) {
        board.setName(createDto.getName());
        board.setMinScore(createDto.getMinScore());
        board.setMaxScore(createDto.getMaxScore());
    }
}
