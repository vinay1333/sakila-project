package com.example.sakilla.DTOresponse;

import com.example.sakilla.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class PartialFilmResponse {
    private final Short id;
    private final String title;
    private final String description;  // New field
    private final int length;          // Updated field from "duration" to "length"

    // Static method to convert a Film entity to PartialFilmResponse DTO
    public static PartialFilmResponse from(Film film) {
        return new PartialFilmResponse(
                film.getId(),
                film.getTitle(),
                film.getDescription(),
                film.getLength()  // Updated method to access "length"
        );
    }
}
