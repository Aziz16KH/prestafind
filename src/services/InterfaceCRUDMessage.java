package services;

import entities.Message;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author zizou
 */
public interface InterfaceCRUDMessage {
    
    public void ajouterMessage(Message message) throws SQLException;

    public void modifierMessage(Message message) throws SQLException;

    public void supprimerMessage(int id) throws SQLException;

    public Message afficheMessage(String expediteur) throws SQLException;
    
}
