package org.lpro.sandwichservice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Commande {

    @Id
    private String id;
    private String nom;
    private String mail;
    private String created_at;
    private String livraison;
    private String updated_at;
    private Integer montant;
    private String status;
    private String remise;
    private String token;
    private String client_id;
    private String ref_paiement;
    private String date_paiement;
    private String mode_paiement;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Item> items;

    Commande() {
        // necessaire pour JPA !
    }

    public Commande(String nom, String mail, String created_at, String livraison, String updated_at, Integer montant, String status, String remise, String token, String client_id, String ref_paiement, String date_paiement, String mode_paiement, Set<Item> items) {
        this.nom = nom;
        this.mail = mail;
        this.created_at = created_at;
        this.livraison = livraison;
        this.updated_at = updated_at;
        this.montant = montant;
        this.status = status;
        this.remise = remise;
        this.token = token;
        this.client_id = client_id;
        this.ref_paiement = ref_paiement;
        this.date_paiement = date_paiement;
        this.mode_paiement = mode_paiement;
        this.items = items;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public String getRemise() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise = remise;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRef_paiement() {
        return ref_paiement;
    }

    public void setRef_paiement(String ref_paiement) {
        this.ref_paiement = ref_paiement;
    }

    public String getDate_paiement() {
        return date_paiement;
    }

    public void setDate_paiement(String date_paiement) {
        this.date_paiement = date_paiement;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLivraison() {
        return livraison;
    }

    public void setLivraison(String livraison) {
        this.livraison = livraison;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }
}