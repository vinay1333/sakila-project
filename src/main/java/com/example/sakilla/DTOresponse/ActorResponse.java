package com.example.sakilla.DTOresponse;

import com.example.sakilla.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ActorResponse {
    private final Short id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final List<PartialFilmResponse> films; // List of PartialFilmResponse

    // Static method to convert an Actor entity to an ActorResponse DTO
    public static ActorResponse from(Actor actor) {
        List<PartialFilmResponse> filmResponses = actor.getFilms().stream()
                .map(PartialFilmResponse::from) // Convert each Film to PartialFilmResponse
                .collect(Collectors.toList());

        return new ActorResponse(
                actor.getId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getFullName(),
                filmResponses // Attach the list of films
        );
    }
}




