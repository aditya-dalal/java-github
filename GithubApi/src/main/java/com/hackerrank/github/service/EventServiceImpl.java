package com.hackerrank.github.service;

import com.google.inject.Inject;
import com.hackerrank.github.dao.EventRepository;
import com.hackerrank.github.exception.InvalidRequestException;
import com.hackerrank.github.model.Event;

import java.util.List;

public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Inject
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void eraseAllEvents() {
        eventRepository.erase();
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Long createEvent(Event event) throws InvalidRequestException {
        Long eventId = eventRepository.create(event);
        if(eventId == null)
            throw new InvalidRequestException("Event already exists", 400);
        return eventId;
    }

    @Override
    public List<Event> getEventsForActor(Long actorId) throws InvalidRequestException {
        List<Event> events = eventRepository.findEventsByActorId(actorId);
        if(events == null || events.isEmpty())
            throw new InvalidRequestException("Actor with id " + actorId + " does not exist", 404);
        return events;
    }
}
