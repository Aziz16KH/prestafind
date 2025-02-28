package com.prestafind.gui.back.reponse;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.prestafind.entities.Question;
import com.prestafind.entities.Reponse;
import com.prestafind.entities.Utilisateurs;
import com.prestafind.services.QuestionService;
import com.prestafind.services.ReponseService;
import com.prestafind.services.UtilisateursService;

import java.util.ArrayList;

public class AjouterReponse extends Form {


    TextField contenuTF;
    Label contenuLabel;
    PickerComponent dateCreationTF;

    ArrayList<Utilisateurs> listUtilisateurss;
    PickerComponent utilisateursPC;
    Utilisateurs selectedUtilisateurs = null;
    ArrayList<Question> listQuestions;
    PickerComponent questionPC;
    Question selectedQuestion = null;


    Button manageButton;

    Form previous;

    public AjouterReponse(Form previous) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] utilisateursStrings;
        int utilisateursIndex;
        utilisateursPC = PickerComponent.createStrings("").label("Utilisateurs");
        listUtilisateurss = UtilisateursService.getInstance().getAll();
        utilisateursStrings = new String[listUtilisateurss.size()];
        utilisateursIndex = 0;
        for (Utilisateurs utilisateurs : listUtilisateurss) {
            utilisateursStrings[utilisateursIndex] = String.valueOf(utilisateurs.getId());
            utilisateursIndex++;
        }
        if (listUtilisateurss.size() > 0) {
            utilisateursPC.getPicker().setStrings(utilisateursStrings);
            utilisateursPC.getPicker().addActionListener(l -> selectedUtilisateurs = listUtilisateurss.get(utilisateursPC.getPicker().getSelectedStringIndex()));
        } else {
            utilisateursPC.getPicker().setStrings("");
        }

        String[] questionStrings;
        int questionIndex;
        questionPC = PickerComponent.createStrings("").label("Question");
        listQuestions = QuestionService.getInstance().getAll();
        questionStrings = new String[listQuestions.size()];
        questionIndex = 0;
        for (Question question : listQuestions) {
            questionStrings[questionIndex] = question.getSujet();
            questionIndex++;
        }
        if (listQuestions.size() > 0) {
            questionPC.getPicker().setStrings(questionStrings);
            questionPC.getPicker().addActionListener(l -> selectedQuestion = listQuestions.get(questionPC.getPicker().getSelectedStringIndex()));
        } else {
            questionPC.getPicker().setStrings("");
        }


        contenuLabel = new Label("Contenu : ");
        contenuLabel.setUIID("labelDefault");
        contenuTF = new TextField();
        contenuTF.setHint("Tapez le contenu");


        dateCreationTF = PickerComponent.createDate(null).label("DateCreation");


        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                contenuLabel, contenuTF,
                dateCreationTF,
                utilisateursPC, questionPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = ReponseService.getInstance().add(
                        new Reponse(


                                selectedUtilisateurs,
                                selectedQuestion,
                                contenuTF.getText(),
                                dateCreationTF.getPicker().getDate()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Reponse ajouté avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de reponse. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutReponse) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (contenuTF.getText().equals("")) {
            Dialog.show("Avertissement", "Contenu vide", new Command("Ok"));
            return false;
        }


        if (dateCreationTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateCreation", new Command("Ok"));
            return false;
        }


        if (selectedUtilisateurs == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateurs", new Command("Ok"));
            return false;
        }

        if (selectedQuestion == null) {
            Dialog.show("Avertissement", "Veuillez choisir un question", new Command("Ok"));
            return false;
        }


        return true;
    }
}