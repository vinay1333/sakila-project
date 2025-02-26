package com.example.sakilla.DTOresponse;

import com.example.sakilla.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

    @Getter
    @AllArgsConstructor
    public class PartialFilmResponse {
        private final Short id;
        private final String title;

        public static PartialFilmResponse from(Film film) {
            return new PartialFilmResponse(film.getId(), film.getTitle());
        }
    }

