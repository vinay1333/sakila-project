package com.example.sakilla;

import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.entities.Actor;
import com.example.sakilla.repos.ActorRepos;
import com.example.sakilla.services.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ActorServiceTest {

    private ActorRepos actorRepos;   // The repository to mock
    private ActorService actorService;  // The service to test

    private List<Actor> actors;

    @BeforeEach
    public void setUp() {
        actorRepos = mock(ActorRepos.class);  // Mock the repository
        actorService = new ActorService(actorRepos);  // Initialize the service with the mock repository

        // Prepare test data
        actors = List.of(
                new Actor((short) 1, "John", "Doe", "John Doe", List.of()),
                new Actor((short) 2, "Jane", "Doe", "Jane Doe", List.of())
        );
    }

    @Test
    public void getActorByIdReturnsActorForValidId() {
        // Arrange
        Actor actor = new Actor((short) 1, "John", "Doe", "John Doe", List.of());
        when(actorRepos.findById((short) 1)).thenReturn(Optional.of(actor));

        // Act
        ActorResponse actualResponse = actorService.getActorById((short) 1); // Call real service method

        // Assert
        assertEquals(actor.getId(), actualResponse.getId());
        assertEquals(actor.getFirstName(), actualResponse.getFirstName());
        assertEquals(actor.getLastName(), actualResponse.getLastName());
    }

    @Test
    public void getActorByIdThrowsExceptionForInvalidId() {
        // Arrange
        when(actorRepos.findById((short) 99)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            actorService.getActorById((short) 99);
        });

        assertEquals("404 NOT_FOUND \"No actor exists with that ID.\"", exception.getMessage());
    }

    @Test
    public void listActorsReturnsAllActors() {
        // Arrange
        when(actorRepos.findAll()).thenReturn(actors);

        // Act
        List<ActorResponse> result = actorService.listActors(Optional.empty()); // Call real method

        // Assert
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Jane Doe", result.get(1).getFullName());
    }

    @Test
    public void createActorReturnsCreatedActor() {
        // Arrange
        Actor savedActor = new Actor((short) 3, "New", "Actor", "New Actor", List.of());
        when(actorRepos.save(any(Actor.class))).thenReturn(savedActor);

        // Act
        ActorResponse result = actorService.createActor("New", "Actor"); // Call real method

        // Assert
        assertEquals(savedActor.getId(), result.getId());
        assertEquals(savedActor.getFirstName(), result.getFirstName());
        assertEquals(savedActor.getLastName(), result.getLastName());
    }

    @Test
    public void updateActorReturnsUpdatedActor() {
        // Arrange
        Actor actor = new Actor((short) 1, "John", "Doe", "John Doe", List.of());
        when(actorRepos.findById((short) 1)).thenReturn(Optional.of(actor));  // Mock actor retrieval
        when(actorRepos.save(any(Actor.class))).thenReturn(new Actor((short) 1, "Updated", "Name", "Updated Name", List.of()));  // Mock save

        // Act
        ActorResponse result = actorService.updateActor((short) 1, "Updated", "Name");

        // Assert
        assertEquals("Updated Name", result.getFullName());
    }

    @Test
    public void patchActorUpdatesActorSuccessfully() {
        // Arrange
        Actor existingActor = new Actor((short) 1, "John", "Doe", "John Doe", List.of());
        when(actorRepos.findById((short) 1)).thenReturn(Optional.of(existingActor)); // Mock finding actor
        when(actorRepos.save(any(Actor.class))).thenReturn(existingActor); // Mock saving the actor

        // Act
        ActorResponse response = actorService.patchActor((short) 1, "Updated", null);

        // Assert
        assertEquals("Updated", response.getFirstName());
        assertEquals("Doe", response.getLastName()); // Last name should remain unchanged
    }

    @Test
    public void patchActorThrowsExceptionForInvalidId() {
        // Arrange
        when(actorRepos.findById((short) 99)).thenReturn(Optional.empty()); // Mock actor not found

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.patchActor((short) 99, "Updated", "Name");
        });
        assertEquals("Actor not found", exception.getMessage());
    }

    @Test
    public void deleteActorDeletesActorForValidId() {
        // Arrange
        Actor actor = new Actor((short) 1, "John", "Doe", "John Doe", List.of());
        when(actorRepos.existsById((short) 1)).thenReturn(true);  // Mocking that the actor exists
        doNothing().when(actorRepos).deleteById((short) 1);  // Mock delete operation

        // Act
        actorService.deleteActor((short) 1);  // Should call the real method and delete actor

        // Assert
        verify(actorRepos, times(1)).deleteById((short) 1); // Verifies that the delete method was called once
    }

    @Test
    public void deleteActorThrowsExceptionForInvalidId() {
        // Arrange
        when(actorRepos.existsById((short) 99)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            actorService.deleteActor((short) 99);
        });

        assertEquals("404 NOT_FOUND \"No actor exists with that ID.\"", exception.getMessage());
    }
}

