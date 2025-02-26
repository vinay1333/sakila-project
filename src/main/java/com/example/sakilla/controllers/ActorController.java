package com.example.sakilla.controllers;

import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.DTOresponse.ActorRequest;
import com.example.sakilla.entities.Actor;
import com.example.sakilla.repos.ActorRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


@RestController
public class ActorController {

    private final ActorRepos actorRepos;

    @Autowired
    public ActorController(ActorRepos actorRepos) {
        this.actorRepos = actorRepos;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping("/actors")
    public List<ActorResponse> listActors(@RequestParam(required = false) Optional<String> name) {
        return name
                .map(actorRepos::findByFullNameContainingIgnoreCase)
                .orElseGet(actorRepos::findAll)
                .stream()
                .map(ActorResponse::from)
                .toList();
    }

    @GetMapping("/actors/{id}")
    public ActorResponse getActorById(@PathVariable Short id) {
        return actorRepos.findById(id)
                .map(ActorResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No actor exists with that ID."));
    }


    @PostMapping("/actors")
    public ActorResponse createActor(@RequestBody ActorRequest data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());

        Actor savedActor = actorRepos.save(actor);

        return ActorResponse.from(savedActor);
    }

    @PutMapping("/actors/{id}")
    public ActorResponse updateActor(@PathVariable Short id, @RequestBody ActorRequest data) {
        return actorRepos.findById(id)
                .map(actor -> {
                    actor.setFirstName(data.getFirstName());
                    actor.setLastName(data.getLastName());
                    Actor updatedActor = actorRepos.save(actor);
                    return ActorResponse.from(updatedActor);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No actor exists with that ID."));
    }

    @DeleteMapping("/actors/{id}")
    public void deleteActor(@PathVariable Short id) {
        if (!actorRepos.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No actor exists with that ID.");
        }
        actorRepos.deleteById(id);
    }


}



