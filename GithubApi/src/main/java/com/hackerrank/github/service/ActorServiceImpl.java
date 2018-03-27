package com.hackerrank.github.service;

import com.google.inject.Inject;
import com.hackerrank.github.dao.ActorRepository;
import com.hackerrank.github.dao.EventRepository;
import com.hackerrank.github.exception.InvalidRequestException;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.ActorStreak;
import com.hackerrank.github.model.Event;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ActorServiceImpl implements ActorService {

    private ActorRepository actorRepository;
    private EventRepository eventRepository;

    @Inject
    public ActorServiceImpl(ActorRepository actorRepository, EventRepository eventRepository) {
        this.actorRepository = actorRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void updateAvatarUrl(Actor actor) throws InvalidRequestException {
        Actor dbActor = actorRepository.findById(actor.getId());
        if(dbActor == null)
            throw new InvalidRequestException("Actor does not exist", 404);
        else if(!dbActor.getLogin().equals(actor.getLogin()))
            throw new InvalidRequestException("Not allowed to update login", 400);
        actorRepository.updateAvatarUrl(actor);
    }

    @Override
    public List<Actor> getAllActors() {
        return eventRepository.findActorsGroupByTotalEventsOrderByDesc();
    }

    @Override
    public List<Actor> getActorsStreak() {
        List<Event> events = eventRepository.findEventsOrderByActorIdCreatedAt();
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
