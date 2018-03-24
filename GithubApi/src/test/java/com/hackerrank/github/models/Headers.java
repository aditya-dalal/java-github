package com.hackerrank.github.models;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

@Getter
public class Headers {
    private String contentType;

    @JsonSetter("Content-Type")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}