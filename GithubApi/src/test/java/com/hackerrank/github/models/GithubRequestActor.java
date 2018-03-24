package com.hackerrank.github.models;

import com.hackerrank.github.model.Actor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubRequestActor {
    private String method;
    private String url;
    private Headers headers;
    private Actor body;
}
