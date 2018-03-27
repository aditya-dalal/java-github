package com.hackerrank.github;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.hackerrank.github.config.GithubConfiguration;
import com.hackerrank.github.dao.ActorDAO;
import com.hackerrank.github.dao.ActorRepository;
import com.hackerrank.github.dao.EventDAO;
import com.hackerrank.github.dao.EventRepository;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import org.hibernate.SessionFactory;

public class GithubModule extends AbstractModule {

    private final HibernateBundle<GithubConfiguration> hibernateBundle = new ScanningHibernateBundle<GithubConfiguration>("com.hackerrank.github.model") {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(GithubConfiguration githubConfiguration) {
            return githubConfiguration.getDatabase();
        }
    };

    public GithubModule(Bootstrap<GithubConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    protected void configure() {
        bind(ActorRepository.class).to(ActorDAO.class);
        bind(EventRepository.class).to(EventDAO.class);
    }

    @Provides
    public SessionFactory provideSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }
}
