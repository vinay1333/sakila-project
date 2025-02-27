package com.example.sakilla.controllers;

import com.example.sakilla.DTOresponse.FilmRequest;
import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@RestController
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
}
