package org.lpro.sandwichservice.boundary;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.lpro.sandwichservice.Exception.NotFound;
import org.lpro.sandwichservice.entity.Commande;
import org.lpro.sandwichservice.entity.Facture;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//Annotation pour controller rest
@RestController
@RequestMapping(value = "/commandes", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Commande.class)
public class CommandeRepresentation {

    private final CommandeResource cr;

    public CommandeRepresentation(CommandeResource cr) {
        this.cr = cr;
    }

    @GetMapping
    public ResponseEntity<?> getAllCommandes(
            @RequestParam(value="page", required = false, defaultValue = "0") Integer page
            , @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit)
             {

        Iterable<Commande> allCommandes = cr.findAll(PageRequest.of(page,limit));

        return new ResponseEntity<>(allCommandes,HttpStatus.OK);
    }

    private Resources<Resource<Commande>> commande2Ressource(Iterable <Commande> commandes) {
        Link selfLink = linkTo(CommandeRepresentation.class).withSelfRel();
        List<Resource<Commande>> commandeResources =  new ArrayList();
        commandes.forEach(commande ->
                commandeResources.add(commandeToResource(commande, false)));
        return new Resources<>(commandeResources, selfLink);
    }

    private Resource<Commande> commande2Ressource(Optional <Commande> commandes) {
        Link selfLink = linkTo(CommandeRepresentation.class)
                .slash(commandes.get().getId())
                .withSelfRel();
        Link sandwichLink = linkTo(CommandeRepresentation.class)
                .slash(commandes.get().getId())
                .slash("items")
                .withRel("items");
        return new Resource<>(commandes.get(), selfLink,sandwichLink);
    }

    private Resource<Facture> commande2RessourceFacture(Facture facture) {
        Link selfLink = linkTo(CommandeRepresentation.class)
                .slash(facture.getId())
                .withSelfRel();
        return new Resource<>(facture, selfLink);
    }

    private Resource<Commande> commandeToResource(Commande commande, Boolean collection) {
        Link selfLink = linkTo(CommandeRepresentation.class)
                .slash(commande.getId())
                .withSelfRel();
        Link sandwichLink = linkTo(CommandeRepresentation.class)
                .slash(commande.getId())
                .slash("items")
                .withRel("items");
        if(collection) {
            Link collectionLink = linkTo(CommandeRepresentation.class).withRel("collection");
            return new Resource<>(commande, selfLink,sandwichLink, collectionLink);
        } else {
            return new Resource<>(commande, selfLink);
        }
    }

    @PostMapping
    public ResponseEntity<?> postMethode(@RequestBody Commande commande) {
        String jwtToken = Jwts.builder().setSubject(commande.getId()).claim("roles", "commande").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        commande.setId(UUID.randomUUID().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        commande.setCreated_at(dateFormat.format(date));
        commande.setUpdated_at(dateFormat.format(date));
        commande.setToken(jwtToken);
        Commande saved = cr.save(commande);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(CommandeRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{commandeId}")
    public ResponseEntity<?> getMethodeAvecId(@PathVariable("commandeId") String id, @RequestHeader(value = "x-lbs-token") String headerToken) {
        Optional<Commande> commande = cr.findById(id);
        if (commande.isPresent()) {
            if (commande.get().getToken().equals(headerToken)) {
                return new ResponseEntity<>(commande2Ressource(commande),HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Vous n'avez pas acces à cette commande",HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Commande Inexistante",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{commandeId}/facture")
    public ResponseEntity<?> getMethodeAvecId2(@PathVariable("commandeId") String id) {
        Optional<Commande> commande = cr.findById(id);

        if (commande.isPresent()) {
            Facture facture = new Facture();

            facture.setId(commande.get().getId());
            facture.setDate_paiement(commande.get().getDate_paiement());
            facture.setMontant(commande.get().getMontant());
            facture.setNom(commande.get().getNom());

            return new ResponseEntity<>(commande2RessourceFacture(facture),HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Commande Inexistante",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{commandeId}")
    public ResponseEntity<?> putMethode(@PathVariable("commandeId") String id,@RequestBody Commande commandeUpdated) throws NotFound {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return cr.findById(id)
                .map(commande -> {
                    commande.setNom(commandeUpdated.getNom());
                    commande.setLivraison(commandeUpdated.getLivraison());
                    commande.setMail(commandeUpdated.getMail());
                    commande.setStatus(commandeUpdated.getStatus());
                    commande.setUpdated_at(dateFormat.format(date));
                    cr.save(commande);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow( () -> new NotFound("Commande inexistante"));
    }

    @DeleteMapping("/{commandeId}")
    public ResponseEntity<?> deleteMethode(@PathVariable("commandeId") String id) throws NotFound {
        return cr.findById(id)
                .map(commande -> {
                    cr.delete(commande);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }).orElseThrow( () -> new NotFound("Commande inexistante"));
    }

}