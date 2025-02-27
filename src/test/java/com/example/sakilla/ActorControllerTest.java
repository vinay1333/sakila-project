package com.example.sakilla;

import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.controllers.ActorController;
import com.example.sakilla.entities.Actor;
import com.example.sakilla.services.ActorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ActorControllerTest {

    static ActorService service = mock(ActorService.class);
    static ActorController controller = new ActorController(service);

    static List<Actor> actors = listOf(
            new Actor((short) 1, "Example", "One", "Example One", listOf()),
            new Actor((short) 1, "Example", "One", "Example One", listOf()),
            new Actor((short) 1, "Example", "One", "Example One", listOf())
    );

    @BeforeAll
    public static void setUp() {
        for (var actor : actors) {
            var actorResponse = ActorResponse.from(actor);
            doReturn(actorResponse).when(service).getActorById(actor.getId());
        }
    }

    @Test
    public void getActorReturnsActorForValidActorId() {

        var expectedResponse = ActorResponse.from(actors.get(0));
        doReturn(expectedResponse).when(service).getActorById((short) 1);

        var actualResponse = controller.getActorById((short) 1);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
        Assertions.assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
    }
}
