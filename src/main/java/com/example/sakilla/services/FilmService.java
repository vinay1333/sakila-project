package com.example.sakilla.services;

import com.example.sakilla.DTOresponse.FilmRequest;
import com.example.sakilla.DTOresponse.PartialFilmResponse;
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
        List<Film> films = title.map(filmRepos::findByTitleContainingIgnoreCase).orElseGet(filmRepos::findAll);
        return films.stream()
                .map(PartialFilmResponse::from)
                .collect(Collectors.toList());
    }

    public PartialFilmResponse getFilmById(Short id) {
        Film film = filmRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id " + id));
        return PartialFilmResponse.from(film);
    }

    public PartialFilmResponse createFilm(FilmRequest filmRequest) {
        Film film = new Film();
        film.setTitle(filmRequest.getTitle());
        film.setDescription(filmRequest.getDescription());
        film.setLength(filmRequest.getLength());
        // Assume a default value for languageId or modify based on logic
        film.setLanguageId(1);

        Film createdFilm = filmRepos.save(film);
        return PartialFilmResponse.from(createdFilm);
    }

    public PartialFilmResponse updateFilm(Short id, FilmRequest filmRequest) {
        Film film = filmRepos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found with ID: " + id));

        film.setTitle(filmRequest.getTitle());
        film.setDescription(filmRequest.getDescription());
        film.setLength(filmRequest.getLength());

        Film updatedFilm = filmRepos.save(film);
        return PartialFilmResponse.from(updatedFilm);
    }

    public PartialFilmResponse partiallyUpdateFilm(Short id, FilmRequest filmRequest) {
        Film film = filmRepos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found with ID: " + id));

        // Only update fields that are not null
        if (filmRequest.getTitle() != null) {
            film.setTitle(filmRequest.getTitle());
        }
        if (filmRequest.getDescription() != null) {
            film.setDescription(filmRequest.getDescription());
        }
        if (filmRequest.getLength() != null) {
            film.setLength(filmRequest.getLength());
        }

        Film updatedFilm = filmRepos.save(film);
        return PartialFilmResponse.from(updatedFilm);
    }

    public void deleteFilm(Short id) {
        if (!filmRepos.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found with ID: " + id);
        }
        filmRepos.deleteById(id);
    }
}



