package com.hackerrank.github.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "actors")
public class Actor {

    @Id
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "avatar_url")
    private String avatar;

    public Actor() {
    }

    public Actor(Long id, String login, String avatar) {
        this.id = id;
        this.login = login;
        this.avatar = avatar;
    }

    @JsonSetter("avatar_url")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
}
