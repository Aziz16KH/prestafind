package services;

import entities.Tag;
import java.util.List;
import java.sql.SQLException;

public interface InterfaceCRUDTag {

    public void ajouterTag(Tag tag) throws SQLException;

    public void modifierTag(Tag tag) throws SQLException;

    public void supprimerTag(String nom) throws SQLException;

    public List<Tag> afficherTags() throws SQLException;
}
