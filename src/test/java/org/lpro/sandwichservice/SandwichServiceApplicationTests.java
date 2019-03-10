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
        Categorie categorie = new Categorie("test","une categorie",null);
        categorie.setId(UUID.randomUUID().toString());
        cr.save(categorie);
        ResponseEntity<String> response = restTemplate.getForEntity("/categories/",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("test");
    }

    @Test
    public void getAllCommande(){
        Commande commande = new Commande ("commande","test@gmail.com","la","mardi","jamais",1,"en livraison","0","token","id","0123","mardi","carte",null);
        commande.setId(UUID.randomUUID().toString());
        comr.save(commande);
        ResponseEntity<String> response = restTemplate.getForEntity("/commandes/",String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("commande");
    }


//	@Test
//	public void contextLoads() {
//	}
//
//	@Autowired
//	private IntervenantResource ir;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Before
//	public void setupContext() {
//		ir.deleteAll();
//	}
//
//	@Test
//	public void getOneAPI() {
//		Intervenant i1 = new Intervenant("Tom","Nancy");
//		i1.setId(UUID.randomUUID().toString());
//		ir.save(i1);
//
//		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants/"+i1.getId(), String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertThat(response.getBody()).contains("Tom");
//	}
//
//	@Test
//	public void getAllAPI() {
//		Intervenant i1 = new Intervenant("Tom","Nancy");
//		i1.setId(UUID.randomUUID().toString());
//		ir.save(i1);
//		Intervenant i2 = new Intervenant("Alice","Nancy");
//		i2.setId(UUID.randomUUID().toString());
//		ir.save(i2);
//
//		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants", String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertThat(response.getBody()).contains("Tom");
//		assertThat(response.getBody()).contains("Alice");
//		List<String> list = JsonPath.read(response.getBody(),"$..nom");
//		assertThat(list).asList().hasSize(2);
//	}
//
//	@Test
//	public void notFoundAPI() {
//		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants/42", String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//	}
//
//	@Test
//	public void postAPI() throws Exception{
//		Intervenant i1 = new Intervenant("Tom","Nancy");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> entite = new HttpEntity<>(this.toJsonString(i1), headers);
//
//		ResponseEntity<?> response  = restTemplate.postForEntity("/intervenants", entite, ResponseEntity.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//		URI location = response.getHeaders().getLocation();
//		response = restTemplate.getForEntity(location, String.class);
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//
//	private String toJsonString(Object o) throws Exception {
//		ObjectMapper map = new ObjectMapper();
//		return map.writeValueAsString(o);
//	}

}
