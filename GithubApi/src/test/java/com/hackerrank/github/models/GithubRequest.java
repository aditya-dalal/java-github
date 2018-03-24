package com.hackerrank.github.models;

import com.hackerrank.github.model.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubRequest {
    private String method;
    private String url;
    private Headers headers;
    private Event body;
}

