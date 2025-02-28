/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import utils.DBConnection;

/**
 *
 * @author zizou
 */
public class CRUDMessage implements InterfaceCRUDMessage {
    
    Connection PrestaFindDB = DBConnection.getConnection();
     
    @Override
    public void ajouterMessage(Message message) throws SQLException {
    String sql = "INSERT INTO message(date, etat, type_m, contenu, expediteur) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = PrestaFindDB.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setObject(1, message.getDateEnvoi());
        stmt.setString(2, message.getEtat().name());
        stmt.setString(3, message.getType_m().name());
        stmt.setString(4, message.getContenu());
        stmt.setObject(5, message.getExpediteur());
        int nbrLignes = stmt.executeUpdate();
        if (nbrLignes == 0) {
            throw new SQLException("Message non ajouté");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                message.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Message non ajouté.");
            }
        }
    }
}

    

    @Override
    public void modifierMessage(Message message) throws SQLException {
        String sql = "UPDATE message SET date = ?, etat = ?, type_m = ?, contenu = ?, expediteur = (SELECT email FROM User WHERE id = ?) WHERE id = ?";
        try (PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
            stmt.setObject(1, message.getDateEnvoi());
            stmt.setString(2, message.getEtat().name());
            stmt.setString(3, message.getType_m().name());
            stmt.setString(4, message.getContenu());
            stmt.setInt(5, message.getId());
            int nbrLignes = stmt.executeUpdate();
            if (nbrLignes == 0) {
                throw new SQLException("Aucune ligne n'a été modifiée.");
            }
        }
    }

    @Override
    public void supprimerMessage(int id) throws SQLException {
        String sql = "DELETE FROM message WHERE id = ?";
        try (PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int nbrLignes = stmt.executeUpdate();
            if (nbrLignes == 0) {
                throw new SQLException("Aucune ligne n'a été supprimée.");
            }
        }
    }
    
    
    @Override
    public Message afficheMessage(String expediteur) throws SQLException {
    String sql = "SELECT * FROM message where expediteur = ?";
    try (PreparedStatement stmt = PrestaFindDB.prepareStatement(sql)) {
        stmt.setString(1, expediteur);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime dateEnvoi = rs.getObject("date", LocalDateTime.class);
                Message.EtatMsg etat = Message.EtatMsg.valueOf(rs.getString("etat"));
                Message.TypeMsg type_m = Message.TypeMsg.valueOf(rs.getString("type_m"));
                String contenu = rs.getString("contenu");
                return new Message(id, dateEnvoi, etat, type_m, contenu, expediteur);
            } else {
                return null;
            }
        }
    }
}

    public Message afficheMessage(int messageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
   
