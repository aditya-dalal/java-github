package com.hackerrank.github.controller;

import com.google.inject.Inject;
import com.hackerrank.github.dao.EventRepository;
import com.hackerrank.github.model.Event;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class EventController {

    private EventRepository eventRepository;

    @Inject
    public EventController(EventRepository repository) {
        this.eventRepository = repository;
    }

    @DELETE
    @Path("/erase")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response eraseAllEvents() {
        eventRepository.erase();
        return Response.ok().build();
    }

    @GET
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @POST
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response createEvent(Event event) {
        Long eventId = eventRepository.create(event);
        if(eventId == null)
            return Response.status(400).build();
        return Response.status(201).build();
    }

    @GET
    @Path("/events/actors/{actorID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getEventsForActor(@PathParam("actorID") Long actorId) {
        List<Event> events = eventRepository.findEventsByActorId(actorId);
        if(events == null || events.isEmpty())
            return Response.status(404).build();
        return Response.ok().entity(events).build();
    }
}
