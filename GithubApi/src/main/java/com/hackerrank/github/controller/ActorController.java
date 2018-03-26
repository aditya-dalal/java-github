package com.hackerrank.github.controller;

import com.hackerrank.github.dao.ActorDAO;
import com.hackerrank.github.dao.EventDAO;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.ActorStreak;
import com.hackerrank.github.model.Event;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Path("/")
public class ActorController {

    private ActorDAO actorDAO;
    private EventDAO eventDAO;

    public ActorController(ActorDAO actorDAO, EventDAO eventDAO) {
        this.actorDAO = actorDAO;
        this.eventDAO = eventDAO;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actors")
    @UnitOfWork
    public Response updateAvatarUrl(Actor actor) {
        Actor dbActor = actorDAO.findById(actor.getId());
        if(dbActor == null)
            return Response.status(404).build();
        else if(!dbActor.getLogin().equals(actor.getLogin()))
            return Response.status(400).build();
        actorDAO.updateAvatarUrl(actor);
        return Response.ok().build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actors")
    @UnitOfWork
    public List<Actor> getAllActors() {
        return eventDAO.findActorsGroupByTotalEventsOrderByDesc();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actors/streak")
    @UnitOfWork
    public List<Actor> getActorsStreak() {
        List<Event> events = eventDAO.findEventsOrderByActorIdCreatedAt();
        return getActorsByMaximumStreak(events);
    }

    private List<Actor> getActorsByMaximumStreak(List<Event> events) {
        List<ActorStreak> actorStreaks = new ArrayList<>();
        ActorStreak actorStreak = new ActorStreak(events.get(0).getActor(), 1, events.get(0).getCreatedAt());
        int streak = 1;
        for(int i = 1; i < events.size(); i++) {
            Event e1 = events.get(i);
            Event e2 = events.get(i-1);
            if(e1.getActor().getId().equals(e2.getActor().getId())) {
                if(isSameDate(e1, e2))
                    continue;
                if(isConsecutiveDate(e1, e2))
                    streak++;
                else {
                    if(actorStreak.getMaxStreak() < streak)
                        actorStreak.setMaxStreak(streak);
                    streak = 1;
                }
            }
            else {
                if(actorStreak.getMaxStreak() < streak)
                    actorStreak.setMaxStreak(streak);
                actorStreaks.add(new ActorStreak(actorStreak.getActor(), actorStreak.getMaxStreak(), actorStreak.getCreatedAt()));
                actorStreak = new ActorStreak(e1.getActor(), 1, e1.getCreatedAt());
                streak = 1;
            }
        }
        if(actorStreak.getMaxStreak() < streak)
            actorStreak.setMaxStreak(streak);
        actorStreaks.add(new ActorStreak(actorStreak.getActor(), actorStreak.getMaxStreak(), actorStreak.getCreatedAt()));

        Collections.sort(actorStreaks);
//        actorStreaks.forEach(a -> System.out.println(a.getActor().getId() +": " + a.getMaxStreak()));
        return actorStreaks.stream().map(ActorStreak::getActor).collect(Collectors.toList());
    }

    private boolean isSameDate(Event e1, Event e2) {
        LocalDate date1 = e1.getCreatedAt().toLocalDateTime().toLocalDate();
        LocalDate date2 = e2.getCreatedAt().toLocalDateTime().toLocalDate();
        return date1.equals(date2);
    }

    private boolean isConsecutiveDate(Event e1, Event e2) {
        LocalDate date1 = e2.getCreatedAt().toLocalDateTime().toLocalDate();
//        LocalDate date2 = e1.getCreatedAt().toLocalDateTime().toLocalDate();
//        if(date1.equals(date2))
//            return true;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(e1.getCreatedAt().getTime()));
        cal.add(Calendar.DAY_OF_YEAR, 1);
        LocalDate prev = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date1.equals(prev);
    }
}
