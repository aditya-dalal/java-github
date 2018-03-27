package com.hackerrank.github.controller;

import com.google.inject.Inject;
import com.hackerrank.github.exception.InvalidRequestException;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.service.ActorService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class ActorController {

    private ActorService actorService;

    @Inject
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actors")
    @UnitOfWork
    public Response updateAvatarUrl(Actor actor) {
        try {
            actorService.updateAvatarUrl(actor);
        } catch (InvalidRequestException e) {
            return Response.status(e.getStatus()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actors")
    @UnitOfWork
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actors/streak")
    @UnitOfWork
    public List<Actor> getActorsStreak() {
        return actorService.getActorsStreak();
    }

}
