package com.prestafind.gui.front.question;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.prestafind.entities.Question;
import com.prestafind.entities.Reponse;
import com.prestafind.gui.front.reponse.AjouterReponse;
import com.prestafind.gui.front.reponse.ModifierReponse;
import com.prestafind.services.QuestionService;
import com.prestafind.services.ReponseService;

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

    public void refresh(boolean needRefresh) {
        this.removeAll();
        if (needRefresh) displayList = null;
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    ArrayList<Question> displayList;

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Question> listQuestions = QuestionService.getInstance().getAll();

        if (displayList == null) displayList = QuestionService.getInstance().getAll();

        Container filtersContainer = new Container(BoxLayout.x());
        filtersContainer.setScrollableX(true);
        filtersContainer.add(new Label("Filters : "));
        filtersContainer.add(makeFilterElementByStatus(listQuestions));

        this.add(filtersContainer);

        if (displayList.size() > 0) {
            for (Question question : displayList) {
                Component model = makeQuestionModel(question);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    ArrayList<Reponse> listReponses;

    private PickerComponent makeFilterElementByStatus(ArrayList<Question> list) {


        listReponses = ReponseService.getInstance().getAll();
        PickerComponent pickerComponent = PickerComponent.createStrings("Status");
        ArrayList<String> stringList = new ArrayList<>();

        for (Question object : list) {
            if (!stringList.contains(String.valueOf(object.getStatus()))) {
                stringList.add(String.valueOf(object.getStatus()));
            }
        }

        String[] strings = new String[stringList.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = stringList.get(i);
        }

        pickerComponent.getPicker().addActionListener(l -> {
            displayList = new ArrayList<>();
            for (Question object : list) {
                if (String.valueOf(object.getStatus()).equalsIgnoreCase(pickerComponent.getPicker().getText())) {
                    displayList.add(object);
                }
            }
            this.refresh(false);
        });

        if (list.size() > 0) {
            pickerComponent.getPicker().setStrings(strings);
        } else {
            pickerComponent.getPicker().setStrings("");
        }

        return pickerComponent;
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


        utilisateursLabel = new Label("Utilisateur : " + question.getUtilisateurs());
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

        questionModel.addAll(
                utilisateursLabel, sujetLabel, descriptionLabel, dateCreationLabel, statusLabel, typeLabel
        );

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


        Label reponsesLabel = new Label("Reponses : ");
        questionModel.add(reponsesLabel);


        if (listReponses.size() > 0) {
            for (Reponse reponse : listReponses) {
                Component model = makeReponseModel(reponse);
                questionModel.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }


        Button addBtn;
        addBtn = new Button("Ajouter une reponse");
        addBtn.setUIID("buttonWhiteCenter");
        addBtn.addActionListener(action -> {
            ModifierReponse.currentReponse = null;
            new AjouterReponse(this, question).show();
        });
        questionModel.add(addBtn);


        return questionModel;
    }


    private Container makeReponseModelWithoutButtons(Reponse reponse) {
        Label utilisateursLabel, contenuLabel, dateCreationLabel;

        Container reponseModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reponseModel.setUIID("containerRounded");


        utilisateursLabel = new Label("Utilisateur : " + reponse.getUtilisateurs());
        utilisateursLabel.setUIID("labelDefault");

        contenuLabel = new Label("Contenu : " + reponse.getContenu());
        contenuLabel.setUIID("labelDefault");

        dateCreationLabel = new Label("DateCreation : " + new SimpleDateFormat("dd-MM-yyyy").format(reponse.getDateCreation()));
        dateCreationLabel.setUIID("labelDefault");

        reponseModel.addAll(
                utilisateursLabel, contenuLabel, dateCreationLabel
        );

        return reponseModel;
    }


    private Component makeReponseModel(Reponse reponse) {
        Button editBtn, deleteBtn;
        Container btnsContainer;

        Container reponseModel = makeReponseModelWithoutButtons(reponse);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            ModifierReponse.currentReponse = reponse;
            new ModifierReponse(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reponse ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ReponseService.getInstance().delete(reponse.getId());

                if (responseCode == 200) {
                    ModifierReponse.currentReponse = null;
                    dlg.dispose();
                    reponseModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reponse. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        reponseModel.add(btnsContainer);

        return reponseModel;
    }


}