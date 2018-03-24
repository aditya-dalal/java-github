package com.hackerrank.github.models;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.hackerrank.github.model.Actor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubResponseActors {
    private int statusCode;
    private Headers headers;
    private Actor[] body;

    @JsonSetter("status_code")
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
