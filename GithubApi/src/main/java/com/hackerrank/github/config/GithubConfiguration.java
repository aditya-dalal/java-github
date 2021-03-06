package com.hackerrank.github.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class GithubConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database;

    @JsonProperty("database")
    public DataSourceFactory getDatabase() {
        return this.database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }
}
