package com.prestafind.entities;

import java.util.Date;

public class Question {

    private int id;
    private Utilisateurs utilisateurs;
    private String sujet;
    private String description;
    private Date dateCreation;
    private int status;
    private String type;

    public Question() {
    }

    public Question(int id, Utilisateurs utilisateurs, String sujet, String description, Date dateCreation, int status, String type) {
        this.id = id;
        this.utilisateurs = utilisateurs;
        this.sujet = sujet;
        this.description = description;
        this.dateCreation = dateCreation;
        this.status = status;
        this.type = type;
    }

    public Question(Utilisateurs utilisateurs, String sujet, String description, Date dateCreation, int status, String type) {
        this.utilisateurs = utilisateurs;
        this.sujet = sujet;
        this.description = description;
        this.dateCreation = dateCreation;
        this.status = status;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Utilisateurs utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}