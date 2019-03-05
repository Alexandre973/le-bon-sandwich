package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.Exception.NotFound;
import org.lpro.sandwichservice.entity.Categorie;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//Annotation pour controller rest
@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Categorie.class)
public class CategorieRepresentation {

    private final CategorieResource cr;

    public CategorieRepresentation(CategorieResource cr) {
        this.cr = cr;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value="page", required = false, defaultValue = "0") Integer page
            , @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

//        return new ResponseEntity<>(cr.findAll(PageRequest.of(page,limit)),HttpStatus.OK);
        return new ResponseEntity<>(categorie2Ressource(cr.findAll()),HttpStatus.OK);
    }

    private Resources<Resource<Categorie>> categorie2Ressource(Iterable <Categorie> categories) {
        Link selfLink = linkTo(CategorieRepresentation.class).withSelfRel();
        List<Resource<Categorie>> categorieResources =  new ArrayList();
        categories.forEach(categorie ->
                categorieResources.add(categorieToResource(categorie, false)));
        return new Resources<>(categorieResources, selfLink);
    }

    private Resource<Categorie> categorieToResource(Categorie categorie, Boolean collection) {
        Link selfLink = linkTo(CategorieRepresentation.class)
                .slash(categorie.getId())
                .withSelfRel();
        Link sandwichLink = linkTo(CategorieRepresentation.class)
                .slash(categorie.getId())
                .slash("sandwichs")
                .withRel("sandwichs");
        if(collection) {
            Link collectionLink = linkTo(CategorieRepresentation.class).withRel("collection");
            return new Resource<>(categorie, sandwichLink,selfLink, collectionLink);
        } else {
            return new Resource<>(categorie, sandwichLink,selfLink);
        }
    }

    @PostMapping
    public ResponseEntity<?> postMethode(@RequestBody Categorie categorie) {
        categorie.setId(UUID.randomUUID().toString());
        Categorie saved = cr.save(categorie);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(CategorieRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{categorieId}")
    public ResponseEntity<?> getMethodeAvecId(@PathVariable("categorieId") String id) {
        return Optional.ofNullable(cr.findById(id))
                .filter(Optional::isPresent)
                .map(categorie -> new ResponseEntity<>(categorie.get(),HttpStatus.OK))
                .orElseThrow( () -> new NotFound("Intervenant inexistant"));
    }

    @PutMapping("/{categorieId}")
    public ResponseEntity<?> putMethode(@PathVariable("categorieId") String id,@RequestBody Categorie categorieUpdated) throws NotFound {
        return cr.findById(id)
                .map(categorie -> {
                    categorie.setDescription(categorieUpdated.getDescription());
                    categorie.setNom(categorieUpdated.getNom());
                    cr.save(categorie);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow( () -> new NotFound("Categorie inexistante"));
    }

    @DeleteMapping("/{categorieId}")
    public ResponseEntity<?> deleteMethode(@PathVariable("categorieId") String id) throws NotFound {
        return cr.findById(id)
                .map(categorie -> {
                    cr.delete(categorie);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow( () -> new NotFound("Categorie inexistante"));
    }

}