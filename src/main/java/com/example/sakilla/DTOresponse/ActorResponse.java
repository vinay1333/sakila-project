package com.example.sakilla.DTOresponse;

import com.example.sakilla.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActorResponse {
    private final Short id;
    private final String firstName;
    private final String lastName;
    private final String fullName;  // Add fullName here

    public static ActorResponse from(Actor actor) {
        return new ActorResponse(
                actor.getId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getFullName()  // Populate fullName
        );
    }
}


