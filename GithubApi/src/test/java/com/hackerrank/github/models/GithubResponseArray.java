package com.hackerrank.github.models;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.hackerrank.github.model.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubResponseArray {
    private int statusCode;
    private Headers headers;
    private Event[] body;

    @JsonSetter("status_code")
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
