package com.prestafind.entities;

import java.util.Date;

public class Reponse {

    private int id;
    private Utilisateurs utilisateurs;
    private Question question;
    private String contenu;
    private Date dateCreation;

    public Reponse() {
    }

    public Reponse(int id, Utilisateurs utilisateurs, Question question, String contenu, Date dateCreation) {
        this.id = id;
        this.utilisateurs = utilisateurs;
        this.question = question;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
    }

    public Reponse(Utilisateurs utilisateurs, Question question, String contenu, Date dateCreation) {
        this.utilisateurs = utilisateurs;
        this.question = question;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }


}