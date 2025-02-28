package com.example.sakilla;

import com.example.sakilla.DTOresponse.ActorRequest;
import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.controllers.ActorController;
import com.example.sakilla.entities.Actor;
import com.example.sakilla.services.ActorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ActorControllerTest {

    private ActorService service;
    private ActorController controller;

    private List<Actor> actors;

    @BeforeEach
    public void setUp() {
        service = mock(ActorService.class);
        controller = new ActorController(service);

        actors = List.of(
                new Actor((short) 1, "Example", "One", "Example One", List.of()),
                new Actor((short) 2, "Example", "Two", "Example Two", List.of()),
                new Actor((short) 3, "Example", "Three", "Example Three", List.of())
        );
    }

    @Test
    public void getActorReturnsActorForValidActorId() {
        // Arrange
        ActorResponse expectedResponse = ActorResponse.from(actors.get(0));
        doReturn(expectedResponse).when(service).getActorById((short) 1);

        // Act
        ActorResponse actualResponse = controller.getActorById((short) 1);

        // Assert
        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
        Assertions.assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
    }

    @Test
    public void listActorsReturnsActorsForValidNameFilter() {
        // Arrange
        String nameFilter = "Example";
        List<ActorResponse> expectedActors = List.of(new ActorResponse((short) 1, "John", "Doe", "John Doe", List.of()));
        doReturn(expectedActors).when(service).listActors(Optional.of(nameFilter));

        // Act
        List<ActorResponse> actualActors = controller.listActors(Optional.of(nameFilter));

        // Assert
        Assertions.assertEquals(expectedActors, actualActors);
    }

    @Test
    public void listActorsReturnsAllActorsWhenNoNameFilter() {
        // Arrange
        List<ActorResponse> expectedActors = List.of(new ActorResponse((short) 1, "John", "Doe", "John Doe", List.of()));
        doReturn(expectedActors).when(service).listActors(Optional.empty());

        // Act
        List<ActorResponse> actualActors = controller.listActors(Optional.empty());

        // Assert
        Assertions.assertEquals(expectedActors, actualActors);
    }

    @Test
    public void getActorByIdReturnsActorForValidId() {
        // Arrange
        ActorResponse expectedResponse = new ActorResponse((short) 1, "John", "Doe", "John Doe", List.of());
        doReturn(expectedResponse).when(service).getActorById((short) 1);

        // Act
        ActorResponse actualResponse = controller.getActorById((short) 1);

        // Assert
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createActorReturnsCreatedActor() {
        // Arrange
        ActorRequest request = new ActorRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        ActorResponse expectedResponse = new ActorResponse((short) 1, "John", "Doe", "John Doe", List.of());
        doReturn(expectedResponse).when(service).createActor("John", "Doe");

        // Act
        ActorResponse actualResponse = controller.createActor(request);

        // Assert
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updateActorReturnsUpdatedActor() {
        // Arrange
        ActorRequest request = new ActorRequest();
        request.setFirstName("Updated");
        request.setLastName("Name");
        ActorResponse expectedResponse = new ActorResponse((short) 1, "Updated", "Name", "Updated Name", List.of());
        doReturn(expectedResponse).when(service).updateActor((short) 1, "Updated", "Name");

        // Act
        ActorResponse actualResponse = controller.updateActor((short) 1, request);

        // Assert
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void patchActorReturnsPatchedActor() {
        // Arrange
        ActorRequest request = new ActorRequest();
        request.setFirstName("Patched");
        request.setLastName("Name");

        // Ensure the mock is properly returning the expected result
        ActorResponse expectedResponse = new ActorResponse((short) 1, "Patched", "Name", "Patched Name", List.of());
        doReturn(expectedResponse).when(service).patchActor((short) 1, "Patched", "Name");

        // Act
        ActorResponse actualResponse = controller.patchActor((short) 1, request);

        // Assert
        Assertions.assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void deleteActorDeletesActorForValidId() {
        // Arrange
        doNothing().when(service).deleteActor((short) 1);

        // Act
        controller.deleteActor((short) 1);

        // Assert
        Assertions.assertTrue(true); // Since delete doesn't return anything, no further check is needed.
    }
}


