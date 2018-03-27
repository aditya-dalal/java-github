package com.hackerrank.github;

import com.hackerrank.github.config.GithubConfiguration;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GithubApplication extends Application<GithubConfiguration> {

    private GuiceBundle<GithubConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<GithubConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<GithubConfiguration>newBuilder()
                .addModule(new GithubModule(bootstrap))
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(GithubConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(GithubConfiguration githubConfiguration, Environment environment) throws Exception {
    }

    public static void main(String[] args) throws Exception{
        new GithubApplication().run(args);
    }
}
