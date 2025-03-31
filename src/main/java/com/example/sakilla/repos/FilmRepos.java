package com.example.sakilla.repos;

import com.example.sakilla.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FilmRepos extends JpaRepository<Film, Short> {
        List<Film> findByTitleContainingIgnoreCase(String title);


        @Query("SELECT f FROM Film f LEFT JOIN FETCH f.categories WHERE f.id = :id")
        Optional<Film> findByIdWithCategories(@Param("id") Short id);

        @Query("SELECT f FROM Film f " +
                "LEFT JOIN FETCH f.categories c " + // Fetch categories associated with the film
                "WHERE (:categoryId IS NULL OR c.id = :categoryId) " + // Filter by categoryId
                "AND (:duration IS NULL OR " +
                "( (:duration = 0 AND f.length < 60) " + // Filter for duration < 60
                "OR (:duration = 1 AND f.length BETWEEN 60 AND 120) " + // Filter for 60 <= duration <= 120
                "OR (:duration = 2 AND f.length > 120) )) " + // Filter for duration > 120
                "AND (:rating IS NULL OR f.rating = :rating) " +
                "AND (:releaseYear IS NULL OR f.releaseYear = :releaseYear) " +
                "AND (:languageId IS NULL OR f.languageId = :languageId)")
        List<Film> findFilmsByFilters(@Param("categoryId") Short categoryId,
                                      @Param("duration") Integer duration, // Kept as Integer
                                      @Param("rating") String rating,
                                      @Param("releaseYear") Integer releaseYear,
                                      @Param("languageId") Integer languageId);

            /* @Query("SELECT f FROM Film f " +
                "LEFT JOIN FETCH f.categories c " + // Fetch categories associated with the film
                "WHERE (:categoryId IS NULL OR c.id = :categoryId) " + // Filter by categoryId
                "AND (:duration IS NULL OR f.length = :duration) " +
                "AND (:rating IS NULL OR f.rating = :rating) " +
                "AND (:releaseYear IS NULL OR f.releaseYear = :releaseYear) " +
                "AND (:languageId IS NULL OR f.languageId = :languageId)")
        List<Film> findFilmsByFilters(@Param("categoryId") Short categoryId,
                                      @Param("duration") Integer duration,
                                      @Param("rating") String rating,
                                      @Param("releaseYear") Integer releaseYear,
                                      @Param("languageId") Integer languageId);*/



}




