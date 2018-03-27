package com.hackerrank.github.service;

import com.hackerrank.github.exception.InvalidRequestException;
import com.hackerrank.github.model.Actor;

import java.util.List;

public interface ActorService {
    void updateAvatarUrl(Actor actor) throws InvalidRequestException;
    List<Actor> getAllActors();
    List<Actor> getActorsStreak();
}
