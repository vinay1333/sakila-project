package com.example.sakilla.controllers;

import com.example.sakilla.DTOresponse.ActorResponse;
import com.example.sakilla.DTOresponse.ActorRequest;
import com.example.sakilla.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // GET all actors or filter by name
    @GetMapping("/actors")
    public List<ActorResponse> listActors(@RequestParam(required = false) Optional<String> name) {
        return actorService.listActors(name);
    }

    // GET actor by ID
    @GetMapping("/actors/{id}")
    public ActorResponse getActorById(@PathVariable Short id) {
        return actorService.getActorById(id);
    }

    // POST create actor
    @PostMapping("/actors")
    public ActorResponse createActor(@RequestBody ActorRequest data) {
        return actorService.createActor(data.getFirstName(), data.getLastName());
    }

    // PUT update actor
    @PutMapping("/actors/{id}")
    public ActorResponse updateActor(@PathVariable Short id, @RequestBody ActorRequest data) {
        return actorService.updateActor(id, data.getFirstName(), data.getLastName());
    }

    // PATCH update actor (partial update)
    @PatchMapping("/actors/{id}")
    public ActorResponse patchActor(@PathVariable Short id, @RequestBody ActorRequest data) {
        return actorService.patchActor(id, data.getFirstName(), data.getLastName());
    }

    // DELETE actor
    @DeleteMapping("/actors/{id}")
    public void deleteActor(@PathVariable Short id) {
        actorService.deleteActor(id);
    }
}





