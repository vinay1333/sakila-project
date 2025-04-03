package com.example.sakilla;

import com.example.sakilla.DTOresponse.PartialFilmResponse;
import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.entities.Film;
import com.example.sakilla.repos.FilmRepos;
import com.example.sakilla.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.sakilla.entities.Actor;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {

    private FilmRepos filmRepos;   // The repository to mock
    private FilmService filmService;  // The service to test

    private List<Film> films;
    private Film testFilm;

    @BeforeEach
    public void setUp() {
        filmRepos = mock(FilmRepos.class);
        filmService = new FilmService(filmRepos);

        // Initialize testFilm with actors
        testFilm = new Film();
        testFilm.setId((short) 1);
        testFilm.setTitle("Test Film");
        testFilm.setDescription("Description");
        testFilm.setLength(120);
        testFilm.setLanguageId(1);
        testFilm.setReleaseYear(2023);
        testFilm.setRating("PG");

        // Add actors to the film
        Actor actor1 = new Actor((short) 1, "John", "Doe", "John Doe", List.of());
        Actor actor2 = new Actor((short) 2, "Jane", "Doe", "Jane Doe", List.of());
        testFilm.setActors(List.of(actor1, actor2));

        // Initialize films list with a test film
        films = List.of(testFilm);
    }

    @Test
    public void getFilmByIdReturnsFilmForValidId() {
        // Arrange
        when(filmRepos.findByIdWithCategories(anyShort())).thenReturn(Optional.of(testFilm));  // Mock repo

        // Act
        PartialFilmResponse actualResponse = filmService.getFilmById((short) 1);  // Call real service method

        // Assert
        assertEquals(testFilm.getId(), actualResponse.getId());
        assertEquals(testFilm.getTitle(), actualResponse.getTitle());
        assertEquals(testFilm.getDescription(), actualResponse.getDescription());
    }

    @Test
    public void getFilmByIdThrowsExceptionForInvalidId() {
        // Arrange
        when(filmRepos.findByIdWithCategories(anyShort())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> filmService.getFilmById((short) 99));
    }

    @Test
    public void listFilmsReturnsFilmsForValidTitle() {
        // Arrange
        when(filmRepos.findByTitleContainingIgnoreCase("Test")).thenReturn(films);  // Mock repo

        // Act
        List<PartialFilmResponse> result = filmService.listFilms(Optional.of("Test"));  // Call real service method

        // Assert
        assertEquals(1, result.size());  // Check if the film is returned
        assertEquals("Test Film", result.get(0).getTitle());  // Ensure the correct film title is returned
    }

    @Test
    public void filterFilmsReturnsFilmsForValidFilter() {
        // Arrange
        when(filmRepos.findFilmsByFilters(anyShort(), any(), any(), any(), any())).thenReturn(films);  // Mock repo

        // Act
        List<PartialFilmResponse> result = filmService.filterFilms((short) 1, 1, "PG", 2023, 1);  // Call real service method

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Film", result.get(0).getTitle());  // Ensure the correct film title is returned
    }

    @Test
    public void getActorsForFilmReturnsActorsForValidFilmId() {
        // Arrange: Mock the repository to return the testFilm when findById is called
        when(filmRepos.findById(anyShort())).thenReturn(Optional.of(testFilm));  // Ensure testFilm is returned for anyShort()

        // Act: Call the service method to fetch the actors
        List<ActorResponse> result = filmService.getActorsForFilm((short) 1);

        // Assert: Check if the result is not null and contains the expected number of actors
        assertNotNull(result);
        assertEquals(2, result.size());

        // Assert: Check the individual actor names (if applicable)
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Jane Doe", result.get(1).getFullName());
    }

    @Test
    public void getActorsForFilmThrowsExceptionForInvalidFilmId() {
        // Arrange: Mock the repository to return empty for an invalid ID
        when(filmRepos.findById(anyShort())).thenReturn(Optional.empty());

        // Act & Assert: Expecting a ResponseStatusException to be thrown
        assertThrows(ResponseStatusException.class, () -> filmService.getActorsForFilm((short) 99));
    }



    @Test
    public void filterFilmsHandlesEmptyFilters() {
        // Arrange
        films = List.of(testFilm);  // Initialize films list with a test film
        when(filmRepos.findFilmsByFilters(null, null, null, null, null)).thenReturn(films);  // Mock empty filter case

        // Act
        List<PartialFilmResponse> result = filmService.filterFilms(null, null, null, null, null);  // Call real service method

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Film", result.get(0).getTitle());
    }
}






