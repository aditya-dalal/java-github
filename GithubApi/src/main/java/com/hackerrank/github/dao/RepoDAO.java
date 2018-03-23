package com.hackerrank.github.dao;

import com.hackerrank.github.model.Repo;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class RepoDAO extends AbstractDAO<Repo> {

    public RepoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
