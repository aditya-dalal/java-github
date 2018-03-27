package com.hackerrank.github.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "repos")
public class Repo {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    public Repo() {
    }

    public Repo(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
