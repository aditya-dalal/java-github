package com.hackerrank.github.dao;

import com.hackerrank.github.model.Actor;

public interface ActorRepository {
    Actor findById(Long id);
    void updateAvatarUrl(Actor actor);
}
