package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.Exception.NotFound;
import org.lpro.sandwichservice.entity.Sandwich;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Sandwich.class)
public class SandwichRepresentation {

    private final SandwichRessource sr;
    private final CategorieResource cr;

    public SandwichRepresentation(SandwichRessource sr, CategorieResource cr) {
        this.sr = sr;
        this.cr = cr;
    }

    @GetMapping("/sandwichs")
    public ResponseEntity<?> getAllSandwich(
            @RequestParam(value="page", required = false, defaultValue = "0") Integer page
            , @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
            , @RequestParam(value = "pain", required = false) String pain
            , @RequestParam(value = "prix", required = false) Integer prix) {
        if (prix != null && pain != null) {
            return new ResponseEntity<>(sandwich2Ressource(sr.findByPrixAndPain(prix,pain)),HttpStatus.OK);
        } else if (prix != null) {
            return new ResponseEntity<>(sandwich2Ressource(sr.findByPrix(prix)),HttpStatus.OK);
        } else if (pain != null) {
            return new ResponseEntity<>(sandwich2Ressource(sr.findByPain(pain)),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(sr.findAll(PageRequest.of(page,limit)),HttpStatus.OK);
        }
    }

    private Resources<Resource<Sandwich>> sandwich2Ressource(Iterable <Sandwich> sandwiches) {
        Link selfLink = linkTo(SandwichRepresentation.class).withSelfRel();
        List<Resource<Sandwich>> sandwichResources =  new ArrayList();
        sandwiches.forEach(sandwich ->
                sandwichResources.add(sandwichToResource(sandwich, false)));
        return new Resources<>(sandwichResources, selfLink);
    }

    private Resource<Sandwich> sandwichToResource(Sandwich sandwich, Boolean collection) {
        Link selfLink = linkTo(SandwichRepresentation.class)
                .slash(sandwich.getId())
                .withSelfRel();
        if(collection) {
            Link collectionLink = linkTo(SandwichRepresentation.class).withRel("collection");
            return new Resource<>(sandwich, selfLink, collectionLink);
        } else {
            return new Resource<>(sandwich, selfLink);
        }
    }

    @GetMapping("/categories/{id_categorie}/sandwichs")
    public ResponseEntity<?> getSandwichByCategorieId(@PathVariable("id_categorie") String id) throws NotFound {
        if (!cr.existsById(id)){
            throw new NotFound("Categorie inexistante");
        }
        return new ResponseEntity<>(sr.findByCategorieId(id), HttpStatus.OK);
    }

    @PostMapping("/categories/{id_categorie}/sandwichs")
    public ResponseEntity<?> ajoutSandwich(@PathVariable("id_categorie") String id, @RequestBody Sandwich sandwich) throws NotFound {
        return cr.findById(id)
                .map(categorie -> {
                    sandwich.setId(UUID.randomUUID().toString());
                    sandwich.setCategorie(categorie);
                    sr.save(sandwich);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }).orElseThrow( () -> new NotFound("Categorie inexistante"));
    }

    @PutMapping("/categories/{id_categorie}/sandwichs/{id_sandwich}")
    public ResponseEntity<?> modificationSandwich(@PathVariable("id_categorie") String id_categorie,@PathVariable("id_sandwich") String id_sandwich, @RequestBody Sandwich sandwichUpdated) throws NotFound {
        if (!cr.existsById(id_categorie)){
            throw new NotFound("Categorie inexistante");
        }
        return sr.findById(id_sandwich).map(sandwich -> {
            sandwich.setNom(sandwichUpdated.getNom());
            sandwich.setDescription(sandwichUpdated.getDescription());
            sandwich.setPain(sandwichUpdated.getPain());
            sandwich.setPrix(sandwichUpdated.getPrix());
            sr.save(sandwich);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseThrow( () -> new NotFound("Sandwich inexistant"));
    }

    @DeleteMapping("/categories/{id_categorie}/sandwichs/{id_sandwich}")
    public ResponseEntity<?> suppressionSandwich(@PathVariable("id_categorie") String id_categorie,@PathVariable("id_sandwich") String id_sandwich) throws NotFound {
        if (!cr.existsById(id_categorie)){
            throw new NotFound("Categorie inexistante");
        }
        return sr.findById(id_sandwich).map(sandwich -> {
            sr.delete(sandwich);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseThrow( () -> new NotFound("Sandwich inexistant"));
    }
}