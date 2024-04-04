package com.epf.rentmanager.models;

import java.time.LocalDate;
public class Client {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(Integer id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;

    }

    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getprenom() {
        return prenom;
    }

    public void setprenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
