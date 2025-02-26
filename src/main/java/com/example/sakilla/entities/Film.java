package com.example.sakilla.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;  // ✅ Correct import
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@Getter
@Setter
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name ="title")
    private String title;

    @ManyToMany(mappedBy = "films")
    private List<Actor> actors = new ArrayList<>();
}

