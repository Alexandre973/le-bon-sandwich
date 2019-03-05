package org.lpro.sandwichservice.boundary;


import org.lpro.sandwichservice.entity.User;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

    private final UserRepository ur;

    public UserRepresentation(UserRepository ur) {
        this.ur = ur;
    }


    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody User user){
        user.setId(Long.valueOf(UUID.randomUUID().toString()));
        User saved = ur.save(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(UserRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


}
