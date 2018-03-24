package com.hackerrank.github.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputResponseBodyArray extends Input {
    private GithubResponseArray response;
}
