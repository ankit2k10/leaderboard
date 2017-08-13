package com.leaderboard.mapper.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * Created by ankit.chaudhury on 14/08/17.
 */
@BindingAnnotation
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBoardMapper {
}
