package com.hackerrank.github.dao;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;

import java.util.List;

public interface EventRepository {
    Event findById(Long id);
    List<Event> findAll();
    Long create(Event event);
    void erase();
    List<Event> findEventsByActorId(Long id);
    List<Actor> findActorsGroupByTotalEventsOrderByDesc();
    List<Event> findEventsOrderByActorIdCreatedAt();
}
