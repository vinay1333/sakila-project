package com.example.sakilla;

import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.controllers.FilmController;
import com.example.sakilla.entities.Film;
import com.example.sakilla.services.FilmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class FilmControllerTest {

    private FilmService service;
    private FilmController controller;
    private List<Film> films;

    @BeforeEach
    public void setUp() {
        service = mock(FilmService.class);
        controller = new FilmController(service);

        films = List.of(
                new Film((short) 1, "Film One", "Description One", 120, 1, 2020, "PG", List.of(), List.of()),
                new Film((short) 2, "Film Two", "Description Two", 130, 1, 2021, "PG-13", List.of(), List.of()),
                new Film((short) 3, "Film Three", "Description Three", 140, 2, 2022, "R", List.of(), List.of())
        );
    }


    @Test
    public void getFilmByIdReturnsFilmForValidId() {
        // Arrange
        PartialFilmResponse expectedResponse = PartialFilmResponse.from(films.get(0));
        doReturn(expectedResponse).when(service).getFilmById((short) 1);

        // Act
        PartialFilmResponse actualResponse = controller.getFilmById((short) 1);

        // Assert
        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getTitle(), actualResponse.getTitle());
        Assertions.assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
    }

    @Test
    public void listFilmsReturnsFilmsForValidTitleFilter() {
        // Arrange
        String titleFilter = "Film";
        List<PartialFilmResponse> expectedFilms = List.of(PartialFilmResponse.from(films.get(0)));
        doReturn(expectedFilms).when(service).listFilms(Optional.of(titleFilter));

        // Act
        List<PartialFilmResponse> actualFilms = controller.listFilms(Optional.of(titleFilter));

        // Assert
        Assertions.assertEquals(expectedFilms.size(), actualFilms.size());
        for (int i = 0; i < expectedFilms.size(); i++) {
            Assertions.assertEquals(expectedFilms.get(i).getId(), actualFilms.get(i).getId());
            Assertions.assertEquals(expectedFilms.get(i).getTitle(), actualFilms.get(i).getTitle());
        }
    }

    @Test
    public void listFilmsReturnsAllFilmsWhenNoTitleFilter() {
        // Arrange
        List<PartialFilmResponse> expectedFilms = List.of(PartialFilmResponse.from(films.get(0)));
        doReturn(expectedFilms).when(service).listFilms(Optional.empty());

        // Act
        List<PartialFilmResponse> actualFilms = controller.listFilms(Optional.empty());

        // Assert
        Assertions.assertEquals(expectedFilms.size(), actualFilms.size());
        for (int i = 0; i < expectedFilms.size(); i++) {
            Assertions.assertEquals(expectedFilms.get(i).getId(), actualFilms.get(i).getId());
            Assertions.assertEquals(expectedFilms.get(i).getTitle(), actualFilms.get(i).getTitle());
        }
    }

    @Test
    public void filterFilmsReturnsFilteredFilms() {
        // Arrange
        Short categoryId = 1;
        Integer duration = 120;
        String rating = "PG";
        Integer releaseYear = 2020;
        Integer languageId = 1;

        List<PartialFilmResponse> expectedFilms = List.of(PartialFilmResponse.from(films.get(0)));
        doReturn(expectedFilms).when(service).filterFilms(categoryId, duration, rating, releaseYear, languageId);

        // Act
        List<PartialFilmResponse> actualFilms = controller.filterFilms(categoryId, duration, rating, releaseYear, languageId);

        // Assert
        Assertions.assertEquals(expectedFilms.size(), actualFilms.size());
        for (int i = 0; i < expectedFilms.size(); i++) {
            Assertions.assertEquals(expectedFilms.get(i).getId(), actualFilms.get(i).getId());
            Assertions.assertEquals(expectedFilms.get(i).getTitle(), actualFilms.get(i).getTitle());
        }
    }

    @Test
    public void getActorsForFilmReturnsActorsForValidFilmId() {
        // Arrange
        List<ActorResponse> expectedActors = List.of(new ActorResponse((short) 1, "John", "Doe", "John Doe", List.of()));
        doReturn(expectedActors).when(service).getActorsForFilm((short) 1);

        // Act
        List<ActorResponse> actualActors = controller.getActorsForFilm((short) 1);

        // Assert
        Assertions.assertEquals(expectedActors.size(), actualActors.size());
        for (int i = 0; i < expectedActors.size(); i++) {
            Assertions.assertEquals(expectedActors.get(i).getId(), actualActors.get(i).getId());
            Assertions.assertEquals(expectedActors.get(i).getFirstName(), actualActors.get(i).getFirstName());
            Assertions.assertEquals(expectedActors.get(i).getLastName(), actualActors.get(i).getLastName());
        }
    }
}


