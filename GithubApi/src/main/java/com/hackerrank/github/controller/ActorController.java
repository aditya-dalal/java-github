package com.hackerrank.github.controller;

import com.hackerrank.github.dao.ActorDAO;

public class ActorController {

    private ActorDAO actorDAO;

    public ActorController(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }
}
