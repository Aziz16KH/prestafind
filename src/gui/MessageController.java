/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entities.Message;
import entities.Message.EtatMsg;
import entities.Message.TypeMsg;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import services.CRUDMessage;

/**
 * FXML Controller class
 *
 * @author zizou
 */
public class MessageController {

    @FXML
    private Label messageLabel;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    public void showMessage(String sender) {
        try {
            CRUDMessage crud = new CRUDMessage();
            Message message = crud.afficheMessage(sender);
            if (message != null) {
                String messageContent = message.getContenu();
                messageLabel.setText(messageContent);
            } else {
                messageLabel.setText("Message not found.");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error retrieving message: " + e.getMessage());
        }
    }

    @FXML
    void sendMessage(ActionEvent event) throws IOException, SQLException {
        String messageContent = messageField.getText();
        LocalDate currentDate = LocalDate.now();
        Date date = Date.valueOf(currentDate);
        Message message = new Message(LocalDateTime.now(),EtatMsg.ENVOYE,TypeMsg.TEXTE,messageContent,"Ahmed");
        CRUDMessage crud = new CRUDMessage();
        crud.ajouterMessage(message);
        messageField.clear();
    }
}
