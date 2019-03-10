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
    public void getCommandeWithNoLogin(){
        Commande commande = new Commande ("commande","test@gmail.com","10/03/2019","15/03/2019","12/03/2019",5,"en cours","","0dfg5165f51h56gfj45ghjphkdkhknsknlfknnkesgeggegege","1","1","15/03/2019","carte",null);
        commande.setId(UUID.randomUUID().toString());
        comr.save(commande);
        ResponseEntity<String> response = restTemplate.getForEntity("/commandes/",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void notFoundAPI() {
        ResponseEntity<String> response = restTemplate.getForEntity("/categories/42", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}