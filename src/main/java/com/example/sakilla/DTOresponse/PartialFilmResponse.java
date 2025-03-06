package com.example.sakilla.DTOresponse;

import com.example.sakilla.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartialFilmResponse {
    private final Short id;
    private final String title;
    private final String description;
    private final int length;
    private final int releaseYear;  // Add releaseYear field
    private final int languageId;
    private final String rating;// Add languageId field

    // Static method to convert a Film entity to PartialFilmResponse DTO
    public static PartialFilmResponse from(Film film) {
        String rating = film.getRatingEnum() != null ? film.getRatingEnum().toString().replace("_", "-") : null;
        return new PartialFilmResponse(
                film.getId(),
                film.getTitle(),
                film.getDescription(),
                film.getLength(),
                film.getReleaseYear(),   // Map releaseYear from Film entity
                film.getLanguageId(),
                rating
        );
    }
}

