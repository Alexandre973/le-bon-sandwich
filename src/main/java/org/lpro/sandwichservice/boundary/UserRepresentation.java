package org.lpro.sandwichservice.boundary;


import org.lpro.sandwichservice.entity.User;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

    private final UserRessource ur;

    public UserRepresentation(UserRessource ur) {
        this.ur = ur;
    }

    @GetMapping
    public ResponseEntity<?> getAllSandwich(){
        return new ResponseEntity<>(ur.findAll(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        System.out.println(user.toString());
        User saved = ur.save(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(UserRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
