package com.example.sakilla.DTOresponse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActorRequest {

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min=1, max=45)
    private String firstName;

    @NotNull(groups = ValidationGroup.Create.class)
    @Size(min=1, max=45)
    private String lastName;
}



