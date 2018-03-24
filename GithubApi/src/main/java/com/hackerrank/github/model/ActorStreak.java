package com.hackerrank.github.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ActorStreak implements Comparable<ActorStreak> {
    private Actor actor;
    private int maxStreak;
    private Timestamp createdAt;

    @Override
    public int compareTo(ActorStreak actorStreak) {
        if(this.maxStreak > actorStreak.maxStreak)
            return 1;
        else if(this.maxStreak < actorStreak.maxStreak)
            return -1;
        LocalDateTime d1 = this.createdAt.toLocalDateTime();
        LocalDateTime d2 = actorStreak.getCreatedAt().toLocalDateTime();
        if(d1.isAfter(d2))
            return 1;
        else if(d1.isBefore(d2))
            return -1;
        return this.actor.getLogin().compareTo(actorStreak.getActor().getLogin());
    }
}
