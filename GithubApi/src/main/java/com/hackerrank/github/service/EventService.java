package com.hackerrank.github.service;

import com.hackerrank.github.exception.InvalidRequestException;
import com.hackerrank.github.model.Event;

import java.util.List;

public interface EventService {

    void eraseAllEvents();
    List<Event> getAllEvents();
    Long createEvent(Event event) throws InvalidRequestException;
    List<Event> getEventsForActor(Long actorId) throws InvalidRequestException;
}
