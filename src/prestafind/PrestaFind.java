package prestafind;


import entities.Message;
import entities.Message.EtatMsg;
import entities.Message.TypeMsg;
import utils.DBConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import services.CRUDMessage;

public class PrestaFind {

    public static void main(String[] args) throws SQLException {
        
        DBConnection PrestaFindDB = DBConnection.getInstance();
        Message m1 = new Message(0,LocalDateTime.now(),EtatMsg.ENVOYE,TypeMsg.TEXTE,"hello","jdoe@example.com");
        Message m2 = new Message(0,LocalDateTime.now(),EtatMsg.LU,TypeMsg.TEXTE,"bonjour","jdoe@example.com");
        CRUDMessage crud = new CRUDMessage();
        //crud.ajouterMessage(m1);
        //crud.supprimerMessage(4);
        //crud.modifierMessage(m2);
     

} 
 }
