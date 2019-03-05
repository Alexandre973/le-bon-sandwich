package org.lpro.sandwichservice.entity;

public class Facture {

    private String id;
    private String nom;
    private Integer montant;
    private String date_paiement;

    public Facture() {
        // necessaire pour JPA !
    }

    public Facture(String id, String nom, Integer montant, String date_paiement) {
        this.id = id;
        this.nom = nom;
        this.montant = montant;
        this.date_paiement = date_paiement;
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

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public String getDate_paiement() {
        return date_paiement;
    }

    public void setDate_paiement(String date_paiement) {
        this.date_paiement = date_paiement;
    }
}