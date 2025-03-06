package com.example.sakilla.repos;

import com.example.sakilla.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepos extends JpaRepository<Film, Short> {
        List<Film> findByTitleContainingIgnoreCase(String title);
}




