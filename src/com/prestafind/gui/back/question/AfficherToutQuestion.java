package com.prestafind.gui.back.question;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.prestafind.entities.Question;
import com.prestafind.services.QuestionService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutQuestion extends Form {

    Form previous;

    public static Question currentQuestion = null;
    Button addBtn;


    public AfficherToutQuestion(Form previous) {
        super("Questions", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Question> listQuestions = QuestionService.getInstance().getAll();


        if (listQuestions.size() > 0) {
            for (Question question : listQuestions) {
                Component model = makeQuestionModel(question);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentQuestion = null;
            new AjouterQuestion(this).show();
        });

    }

    Label utilisateursLabel, sujetLabel, descriptionLabel, dateCreationLabel, statusLabel, typeLabel;


    private Container makeModelWithoutButtons(Question question) {
        Container questionModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        questionModel.setUIID("containerRounded");


        utilisateursLabel = new Label("Utilisateurs : " + question.getUtilisateurs());
        utilisateursLabel.setUIID("labelDefault");

        sujetLabel = new Label("Sujet : " + question.getSujet());
        sujetLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + question.getDescription());
        descriptionLabel.setUIID("labelDefault");

        dateCreationLabel = new Label("DateCreation : " + new SimpleDateFormat("dd-MM-yyyy").format(question.getDateCreation()));
        dateCreationLabel.setUIID("labelDefault");

        statusLabel = new Label("Status : " + (question.getStatus() == 1 ? "True" : "False"));
        statusLabel.setUIID("labelDefault");

        typeLabel = new Label("Type : " + question.getType());
        typeLabel.setUIID("labelDefault");

        utilisateursLabel = new Label("Utilisateurs : " + question.getUtilisateurs().getId());
        utilisateursLabel.setUIID("labelDefault");

        questionModel.addAll(

                utilisateursLabel, sujetLabel, descriptionLabel, dateCreationLabel, statusLabel, typeLabel
        );

        if (question.getDateCreation() != null) {
            Calendar calendar = new Calendar();
            calendar.setFocusable(false);
            calendar.setDate(question.getDateCreation());
            calendar.highlightDate(question.getDateCreation(), "dateStart");
            questionModel.add(calendar);
        }

        return questionModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeQuestionModel(Question question) {

        Container questionModel = makeModelWithoutButtons(question);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentQuestion = question;
            new ModifierQuestion(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce question ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = QuestionService.getInstance().delete(question.getId());

                if (responseCode == 200) {
                    currentQuestion = null;
                    dlg.dispose();
                    questionModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du question. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        questionModel.add(btnsContainer);

        return questionModel;
    }

}