package com.example.sakilla.DTOresponse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilmRequest {

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 100)
    private String title;

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 255)
    private String description;

    @NotNull(groups = ValidationGroup.Create.class)
    private Integer length;

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min = 1, max = 5)
    private String rating;  // Rating is still a string for client input
}
