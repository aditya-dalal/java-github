package com.hackerrank.github;

import com.hackerrank.github.config.GithubConfiguration;
import com.hackerrank.github.controller.ActorController;
import com.hackerrank.github.controller.EventController;
import com.hackerrank.github.controller.RepoController;
import com.hackerrank.github.dao.*;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GithubApplication extends Application<GithubConfiguration> {

    private final HibernateBundle<GithubConfiguration> githubBundle = new ScanningHibernateBundle<GithubConfiguration>("com.hackerrank.github.model") {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(GithubConfiguration githubConfiguration) {
            return githubConfiguration.getDatabase();
        }
    };

    @Override
    public void initialize(Bootstrap<GithubConfiguration> bootstrap) {
        bootstrap.addBundle(githubBundle);
    }

    @Override
    public void run(GithubConfiguration githubConfiguration, Environment environment) throws Exception {
        registerActorController(environment);
        registerRepoController(environment);
        registerEventController(environment);
    }

    private void registerEventController(Environment environment) {
        final EventDAO eventDAO = new EventDAO(githubBundle.getSessionFactory());
        environment.jersey().register(new EventController(eventDAO));
    }

    private void registerRepoController(Environment environment) {
        final RepoDAO repoDAO = new RepoDAO(githubBundle.getSessionFactory());
        environment.jersey().register(new RepoController(repoDAO));
    }

    private void registerActorController(Environment environment) {
        final ActorDAO actorDAO = new ActorDAO(githubBundle.getSessionFactory());
        environment.jersey().register(new ActorController(actorDAO));
    }

    public static void main(String[] args) throws Exception{
        new GithubApplication().run(args);
    }
}
