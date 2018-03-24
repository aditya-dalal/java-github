package com.hackerrank.github.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputResponseBodyObject extends Input {
    private GithubResponseObject response;
}
