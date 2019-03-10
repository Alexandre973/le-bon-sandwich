package org.lpro.sandwichservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lpro.sandwichservice.boundary.CategorieResource;
import org.lpro.sandwichservice.boundary.CommandeResource;
import org.lpro.sandwichservice.boundary.UserRessource;
import org.lpro.sandwichservice.entity.Categorie;
import org.lpro.sandwichservice.entity.Commande;
import org.lpro.sandwichservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichServiceApplicationTests {
    @Autowired
    private UserRessource ur;

    @Autowired
    private CategorieResource cr;

    @Autowired
    private CommandeResource comr;

    @Autowired
    private TestRestTemplate restTemplate;


    @Before
    public void setupContext(){
        ur.deleteAll();
        cr.deleteAll();
        comr.deleteAll();
    }
    @Test
    public void getAllUser(){
        User user = new User("patrick","123","hernandez","patrick.hernandez");
        user.setId(UUID.randomUUID().toString());
        ur.save(user);
        ResponseEntity<String> response = restTemplate.getForEntity("/users/",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("patrick");
    }

    @Test
    public void getAllCategorie(){
        Categorie categorie = new Categorie("1","test","un sandwitch",null);
        categorie.setId(UUID.randomUUID().toString());
        cr.save(categorie);
        ResponseEntity<String> response = restTemplate.getForEntity("/categories/",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("test");
    }

    @Test
    public void getAllCommande(){
        Commande commande = new Commande ("1","commande","test@gmail.com","la","mardi","null",5,"en livraison","0","token","1","1","12/02/18","carte",null);
        commande.setId(UUID.randomUUID().toString());
        comr.save(commande);
        ResponseEntity<String> response = restTemplate.getForEntity("/commandes/",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("commande");
    }
}
