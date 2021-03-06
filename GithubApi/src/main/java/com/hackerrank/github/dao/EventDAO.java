package com.hackerrank.github.dao;

import com.google.inject.Inject;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class EventDAO extends AbstractDAO<Event> implements EventRepository {

    @Inject
    public EventDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Event findById(Long id) {
        return get(id);
    }

    public List<Event> findAll() {
        return list(namedQuery("event.findAll"));
    }

    public Long create(Event event) {
        if(findById(event.getId()) != null)
            return null;
        return persist(event).getId();
    }

    public void erase() {
        namedQuery("event.deleteAll").executeUpdate();
    }

    public List<Event> findEventsByActorId(Long actorId) {
        return list(namedQuery("event.findAllByActorOrderByEventId").setParameter("actorId", actorId));
    }

    public List<Actor> findActorsGroupByTotalEventsOrderByDesc() {
        return list(namedQuery("event.findActorsGroupByTotalEventsOrderByDesc"));
    }

    public List<Event> findEventsOrderByActorIdCreatedAt() {
        return list(namedQuery("event.findEventsOrderByActorIdCreatedAt"));
    }
}
