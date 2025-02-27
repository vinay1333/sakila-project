package com.example.sakilla.entities;

import jakarta.persistence.*;
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

    @Column(name = "title")
    private String title;

    @Column(name = "description")   // Added description field
    private String description;      // Field for description

    @Column(name = "length")        // Updated from "duration" to "length"
    private int length;              // Updated field to "length"

    @Column(name = "rating")        // Added rating field
    private String rating;           // Field for rating (assuming you store it as a string)

    @ManyToMany(mappedBy = "films")
    private List<Actor> actors = new ArrayList<>();
}
