package com.hackerrank.github.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputRequestBodyActor {
    private GithubRequestActor request;
    private GithubResponseObject response;
}
