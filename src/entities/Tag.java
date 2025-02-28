package entities;

import java.util.Objects;

public class Tag {

    String nom, description;
    int nbrArticles;

    public Tag() {
    }

    public Tag(String nom, String description, int nbrArticles) {
        this.nom = nom;
        this.description = description;
        this.nbrArticles = nbrArticles;
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

    public int getNbrArticles() {
        return nbrArticles;
    }

    public void setNbrArticles(int nbrArticles) {
        this.nbrArticles = nbrArticles;
    }

    @Override
    public String toString() {
        return "Tag{" + "nom=" + nom + ", description=" + description + ", nbrArticles=" + nbrArticles + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.nom);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tag other = (Tag) obj;
        return Objects.equals(this.nom, other.nom);
    }

}
