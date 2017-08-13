package com.leaderboard.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateDto {
    @NotEmpty
    @JsonProperty("email_id")
    private String emailId;

    @NotEmpty
    private String name;
}
