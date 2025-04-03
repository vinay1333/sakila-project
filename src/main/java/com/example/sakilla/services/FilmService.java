package com.example.sakilla.services;

import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.DTOresponse.FilmRequest;
import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.entities.Actor;
import com.example.sakilla.entities.Film;
import com.example.sakilla.repos.FilmRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepos filmRepos;

    @Autowired
    public FilmService(FilmRepos filmRepos) {
        this.filmRepos = filmRepos;
    }

    public List<PartialFilmResponse> listFilms(Optional<String> title) {
        List<Film> films = title.map(filmRepos::findByTitleContainingIgnoreCase)
                .orElseGet(filmRepos::findAll);
        return films.stream()
                .map(PartialFilmResponse::from)
                .collect(Collectors.toList());
    }

    public PartialFilmResponse getFilmById(Short id) {
        Film film = filmRepos.findByIdWithCategories(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id " + id));
        return PartialFilmResponse.from(film);
    }

    public List<ActorResponse> getActorsForFilm(Short filmId) {
        // Find the film by ID
        Film film = filmRepos.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found"));

        // Get the actors associated with the film
        List<Actor> actors = film.getActors();

        // Convert to ActorResponse DTO and return
        return actors.stream()
                .map(ActorResponse::from)
                .collect(Collectors.toList());
    }

    public List<PartialFilmResponse> filterFilms(Short categoryId, Integer duration, String rating, Integer releaseYear, Integer languageId) {
        List<Film> films = filmRepos.findFilmsByFilters(categoryId, duration, rating, releaseYear, languageId);
        return films.stream()
                .map(PartialFilmResponse::from)
                .collect(Collectors.toList());
    }

}



