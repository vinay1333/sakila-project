package com.example.sakilla.repos;

import com.example.sakilla.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepos extends JpaRepository<Film, Short>{
        List<Film> findByFullNameContainingIgnoreCase(String title);
}
