package com.example.sakilla.entities;

import com.example.sakilla.repos.ActorRepos;
import com.example.sakilla.DTOresponse.ActorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepos actorRepos;

    @Autowired
    public ActorService(ActorRepos actorRepos) {
        this.actorRepos = actorRepos;
    }

    public List<ActorResponse> listActors(Optional<String> name) {
        return name
                .map(actorRepos::findByFullNameContainingIgnoreCase)
                .orElseGet(actorRepos::findAll)
                .stream()
                .map(ActorResponse::from)
                .toList();
    }

    public ActorResponse getActorById(Short id) {
        return actorRepos.findById(id)
                .map(ActorResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No actor exists with that ID."));
    }

    public ActorResponse createActor(String firstName, String lastName) {
        Actor actor = new Actor();
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        Actor savedActor = actorRepos.save(actor);
        return ActorResponse.from(savedActor);
    }

    public ActorResponse updateActor(Short id, String firstName, String lastName) {
        Actor actor = actorRepos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No actor exists with that ID."));
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        Actor updatedActor = actorRepos.save(actor);
        return ActorResponse.from(updatedActor);
    }

    public void deleteActor(Short id) {
        if (!actorRepos.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No actor exists with that ID.");
        }
        actorRepos.deleteById(id);
    }
}

