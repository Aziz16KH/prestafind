package com.prestafind.entities;

public class Utilisateurs {

    private int id;

    public Utilisateurs() {
    }

    public Utilisateurs(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}