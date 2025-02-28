/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author zizou
 */
public class ContactsListController {

    @FXML
    private VBox contactsList;

    @FXML
    private Label nameLabel;

    public void displayContactName(int userId) {

        // Connect to the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password")) {

            // Prepare the SQL statement
            String sql = "SELECT nom FROM utilisateurs WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Get the name of the user
            String name = "";
            if (rs.next()) {
                name = rs.getString("nom");
            }

            // Display the name in the label
            nameLabel.setText(name);
            contactsList.getChildren().add(nameLabel);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
   
}
