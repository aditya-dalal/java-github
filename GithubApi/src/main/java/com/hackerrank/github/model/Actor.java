package com.hackerrank.github.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "actors")
@NamedQueries({
        @NamedQuery(name = "actor.updateAvatarUrl", query = "update Actor a set a.avatar = :avatarUrl where a.id = :id")
})
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
