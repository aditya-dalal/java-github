package com.hackerrank.github.controller;

import com.google.inject.Inject;
import com.hackerrank.github.exception.InvalidRequestException;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.EventService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class EventController {

    private EventService eventService;

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @DELETE
    @Path("/erase")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response eraseAllEvents() {
        eventService.eraseAllEvents();
        return Response.ok().build();
    }

    @GET
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @POST
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response createEvent(Event event) {
        try {
            eventService.createEvent(event);
        } catch (InvalidRequestException e) {
            return Response.status(e.getStatus()).build();
        }
        return Response.status(201).build();
    }

    @GET
    @Path("/events/actors/{actorID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getEventsForActor(@PathParam("actorID") Long actorId) {
        List<Event> events = null;
        try {
            events = eventService.getEventsForActor(actorId);
        } catch (InvalidRequestException e) {
            return Response.status(e.getStatus()).build();
        }
        return Response.ok().entity(events).build();
    }
}
