package services;

import entities.Tag;
import utils.DBConnection;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CRUDTag implements InterfaceCRUDTag {

    Connection PrestaFindDB = DBConnection.getConnection();

    @Override
    public void ajouterTag(Tag tag) throws SQLException {
        String sql = "INSERT INTO tags (nom, description, nbrArticles) VALUES (?, ?, ?)";
        try ( PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
            stmt.setString(1, tag.getNom());
            stmt.setString(2, tag.getDescription());
            stmt.setInt(3, tag.getNbrArticles());
            stmt.executeUpdate();
        }
    }

    @Override
    public void modifierTag(Tag tag) throws SQLException {
        String sql = "UPDATE tags SET nom = ?, description = ?, nbrArticles = ? WHERE nom = ?";
        try ( PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
            stmt.setString(1, tag.getNom());
            stmt.setString(2, tag.getDescription());
            stmt.setInt(3, tag.getNbrArticles());
            stmt.setString(4, tag.getNom());
            stmt.executeUpdate();
        }
    }

    @Override
    public void supprimerTag(String nom) throws SQLException {
        String sql = "DELETE FROM tags WHERE nom = ?";
        try ( PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Tag> afficherTags() throws SQLException {
        String sql = "SELECT id, nom, description, nbrArticles FROM tags";
        List<Tag> tags;
        try ( Statement stmt = PrestaFindDB.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            tags = new ArrayList<>();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setNom(rs.getString("nom"));
                tag.setDescription(rs.getString("description"));
                tag.setNbrArticles(rs.getInt("nbrArticles"));
                tags.add(tag);
            }
        }
        return tags;
    }

    public Tag getTagByNom(String nom) throws SQLException {
        String sql = "SELECT nom, description, nbrArticles FROM tags WHERE id = ?";
        Tag tag;
        try ( PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
            stmt.setString(1, nom);
            try ( ResultSet rs = stmt.executeQuery()) {
                tag = null;
                if (rs.next()) {
                    tag = new Tag();
                    tag.setNom(rs.getString("nom"));
                    tag.setDescription(rs.getString("description"));
                    tag.setNbrArticles(rs.getInt("nbrArticles"));
                }
            }
        }
        return tag;
    }

}
