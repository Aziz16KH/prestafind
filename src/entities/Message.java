/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDateTime;
/**
 *
 * @author zizou
 */
public class Message {
    private int id;
    private LocalDateTime dateEnvoi;
    private EtatMsg etat;
    private TypeMsg type_m;
    private String contenu;
    private String expediteur;

    public Message(int id, LocalDateTime dateEnvoi, EtatMsg etat, TypeMsg type_m, String contenu, String expediteur) {
        this.id = id;
        this.dateEnvoi = dateEnvoi;
        this.etat = etat;
        this.type_m = type_m;
        this.contenu = contenu;
        this.expediteur = expediteur;
    }
    
    public Message(LocalDateTime dateEnvoi, EtatMsg etat, TypeMsg type_m, String contenu, String expediteur) {
        this.id = -1;
        this.dateEnvoi = dateEnvoi;
        this.etat = etat;
        this.type_m = type_m;
        this.contenu = contenu;
        this.expediteur = expediteur;
    }


    public int getId() {
        return id;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public EtatMsg getEtat() {
        return etat;
    }

    public TypeMsg getType_m() {
        return type_m;
    }

    public String getContenu() {
        return contenu;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public void setEtat(EtatMsg etat) {
        this.etat = etat;
    }

    public void setType_m(TypeMsg type_m) {
        this.type_m = type_m;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }
    
    public enum EtatMsg {
        ENATTENTE,
        ENVOYE,
        LU,
        SUPPRIME
    }

    public enum TypeMsg {
        TEXTE,
        FICHIER
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
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
        final Message other = (Message) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", dateEnvoi=" + dateEnvoi + ", etat=" + etat + ", type_m=" + type_m + ", contenu=" + contenu + ", expediteur=" + expediteur + '}';
    }
    
    
    
    
    
}