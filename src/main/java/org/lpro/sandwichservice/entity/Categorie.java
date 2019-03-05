package org.lpro.sandwichservice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Categorie {

    @Id
    private String id;
    private String nom;
    private String description;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sandwich> sandwichs;

    Categorie() {
        // necessaire pour JPA !
    }

    public Categorie(String nom, String description, Set<Sandwich> sandwichs) {
        this.nom = nom;
        this.description = description;
        this.sandwichs = sandwichs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Sandwich> getSandwichs() {
        return sandwichs;
    }

    public void setSandwichs(Set<Sandwich> sandwichs) {
        this.sandwichs = sandwichs;
    }
}