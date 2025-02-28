package com.prestafind.gui.back.question;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.prestafind.entities.Question;
import com.prestafind.entities.Utilisateurs;
import com.prestafind.services.QuestionService;
import com.prestafind.services.UtilisateursService;

import java.util.ArrayList;

public class AjouterQuestion extends Form {


    TextField sujetTF;
    TextField descriptionTF;
    TextField typeTF;
    Label sujetLabel;
    Label descriptionLabel;
    Label typeLabel;
    PickerComponent dateCreationTF;
    CheckBox statusCB;
    ArrayList<Utilisateurs> listUtilisateurss;
    PickerComponent utilisateursPC;
    Utilisateurs selectedUtilisateurs = null;


    Button manageButton;

    Form previous;

    public AjouterQuestion(Form previous) {
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


        sujetLabel = new Label("Sujet : ");
        sujetLabel.setUIID("labelDefault");
        sujetTF = new TextField();
        sujetTF.setHint("Tapez le sujet");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        dateCreationTF = PickerComponent.createDate(null).label("DateCreation");


        statusCB = new CheckBox("Status : ");


        typeLabel = new Label("Type : ");
        typeLabel.setUIID("labelDefault");
        typeTF = new TextField();
        typeTF.setHint("Tapez le type");


        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                sujetLabel, sujetTF,
                descriptionLabel, descriptionTF,
                dateCreationTF,
                statusCB,
                typeLabel, typeTF,
                utilisateursPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = QuestionService.getInstance().add(
                        new Question(


                                selectedUtilisateurs,
                                sujetTF.getText(),
                                descriptionTF.getText(),
                                dateCreationTF.getPicker().getDate(),
                                statusCB.isSelected() ? 1 : 0,
                                typeTF.getText()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Question ajouté avec succes", new Command("Ok"));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de question. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutQuestion) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (sujetTF.getText().equals("")) {
            Dialog.show("Avertissement", "Sujet vide", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (dateCreationTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateCreation", new Command("Ok"));
            return false;
        }


        if (typeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Type vide", new Command("Ok"));
            return false;
        }


        if (selectedUtilisateurs == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateurs", new Command("Ok"));
            return false;
        }


        return true;
    }
}