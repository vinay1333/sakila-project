package com.example.sakilla.controllers;

import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.DTOresponse.FilmRequest;
import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.entities.Film;
import com.example.sakilla.services.FilmService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // GET all films or filter by title
    @GetMapping("/films")
    public List<PartialFilmResponse> listFilms(@RequestParam(required = false) Optional<String> title) {
        return filmService.listFilms(title);
    }

    // GET film by ID
    @GetMapping("/films/{id}")
    public PartialFilmResponse getFilmById(@PathVariable Short id) {
        return filmService.getFilmById(id);
    }

    @GetMapping("/films/filter")
    public List<PartialFilmResponse> filterFilms(
            @RequestParam(required = false) Short categoryId,
            @RequestParam(required = false) Integer duration,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) Integer languageId) {
        return filmService.filterFilms(categoryId, duration, rating, releaseYear, languageId);
    }

    @GetMapping("/films/{id}/actors")
    public List<ActorResponse> getActorsForFilm(@PathVariable Short id) {
        return filmService.getActorsForFilm(id);
    }
}

