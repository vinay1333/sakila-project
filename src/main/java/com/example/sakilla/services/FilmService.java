package com.example.sakilla.services;

import com.example.sakilla.DTOresponse.FilmRequest;
import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.entities.Film;
import com.example.sakilla.entities.Rating;
import com.example.sakilla.repos.FilmRepos;
import jakarta.persistence.EntityNotFoundException;
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
        Film film = filmRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));
        return PartialFilmResponse.from(film);
    }

    public Film createFilm(FilmRequest filmRequest) {
        Film film = new Film();
        film.setTitle(filmRequest.getTitle());
        film.setDescription(filmRequest.getDescription());
        film.setLength(filmRequest.getLength());

        // Set default language_id to 1 (you can adjust this value as needed)
        film.setLanguageId(1); // Add this line

        return filmRepos.save(film);
    }

}
