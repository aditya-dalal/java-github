package com.hackerrank.github.dao;

import com.hackerrank.github.model.Actor;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class ActorDAO extends AbstractDAO<Actor> {

    public ActorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Actor findById(Long id) {
        return get(id);
    }

    public void updateAvatarUrl(Actor actor) {
        namedQuery("actor.updateAvatarUrl")
                .setParameter("avatarUrl", actor.getAvatar())
                .setParameter("id", actor.getId())
                .executeUpdate();
    }

}
