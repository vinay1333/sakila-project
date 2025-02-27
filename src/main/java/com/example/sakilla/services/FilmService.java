package com.example.sakilla.services;

import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.entities.Film;
import com.example.sakilla.entities.Rating;
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
        List<Film> films;
        try {
            if (title.isPresent()) {
                films = filmRepos.findByTitleContainingIgnoreCase(title.get());
            } else {
                films = filmRepos.findAll();
            }
            return films.stream()
                    .map(PartialFilmResponse::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving films", e);
        }
    }

    public PartialFilmResponse getFilmById(Short id) {
        try {
            Film film = filmRepos.findById(id)
                    .orElseThrow(() -> new RuntimeException("Film not found"));
            return PartialFilmResponse.from(film);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving film by ID", e);
        }
    }
}
