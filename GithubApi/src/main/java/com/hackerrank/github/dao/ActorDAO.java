package com.hackerrank.github.dao;

import com.hackerrank.github.model.Actor;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class ActorDAO extends AbstractDAO<Actor> {

    public ActorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
