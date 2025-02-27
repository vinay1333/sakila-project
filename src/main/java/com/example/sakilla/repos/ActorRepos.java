package com.example.sakilla.repos;

import com.example.sakilla.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActorRepos extends JpaRepository<Actor, Short> {
    List<Actor> findByFullNameContainingIgnoreCase(String fullName);
}



