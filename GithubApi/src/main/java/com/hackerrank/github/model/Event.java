package com.hackerrank.github.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(name = "event.deleteAll", query = "delete from Event"),
        @NamedQuery(name = "event.findAll", query = "select e from Event e order by e.id"),
        @NamedQuery(name = "event.findAllByActorOrderByEventId", query = "select e from Event e where e.actor.id = :actorId order by e.id"),
        @NamedQuery(name = "event.findActorsGroupByTotalEventsOrderByDesc", query = "select e.actor from Event e group by e.actor.id order by count(e.actor.id) desc, max(e.createdAt) desc, e.actor.login"),
        @NamedQuery(name = "event.findEventsOrderByActorIdCreatedAt", query = "select e from Event e order by e.actor.id, e.createdAt desc")
})
public class Event {
    @Id
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToOne(targetEntity = Actor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @OneToOne(targetEntity = Repo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "repo_id")
    private Repo repo;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp createdAt;

    public Event() {
    }

    public Event(Long id, String type, Actor actor, Repo repo, Timestamp createdAt) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.repo = repo;
        this.createdAt = createdAt;
    }

    @JsonSetter("created_at")
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
