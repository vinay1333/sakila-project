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
    private int length;

    @Column(name = "language_id")
    private int languageId;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "rating")
    private String rating;


    // Convert the rating string (from the database) to the Rating enum
    public Rating getRatingEnum() {
        return rating != null ? Rating.valueOf(rating.replace("-", "_")) : null;
    }

    // Convert the Rating enum back to a string (for saving to the database)
    public void setRatingEnum(Rating ratingEnum) {
        this.rating = ratingEnum != null ? ratingEnum.name().replace("_", "-") : null;
    }

    @ManyToMany(mappedBy = "films")
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Short getCategoryId() {
        return categories != null && !categories.isEmpty() ? categories.get(0).getId() : null;
    }



}