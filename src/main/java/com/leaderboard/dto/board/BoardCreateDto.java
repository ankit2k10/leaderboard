package com.leaderboard.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardCreateDto {
    @NotEmpty
    private String name;

    @NotNull
    @Min(value = 0, message = "Min value should be non-negative")
    private Integer minScore;

    @NotNull
    @Max(value = 10000, message = "Max allowed value is 10000")
    private Integer maxScore;
}
