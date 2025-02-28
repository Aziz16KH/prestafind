package com.prestafind.gui.back.reponse;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.prestafind.entities.Reponse;
import com.prestafind.services.ReponseService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutReponse extends Form {

    Form previous;

    public static Reponse currentReponse = null;
    Button addBtn;


    public AfficherToutReponse(Form previous) {
        super("Reponses", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Reponse> listReponses = ReponseService.getInstance().getAll();


        if (listReponses.size() > 0) {
            for (Reponse reponse : listReponses) {
                Component model = makeReponseModel(reponse);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentReponse = null;
            new AjouterReponse(this).show();
        });

    }

    Label utilisateursLabel, questionLabel, contenuLabel, dateCreationLabel;


    private Container makeModelWithoutButtons(Reponse reponse) {
        Container reponseModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reponseModel.setUIID("containerRounded");


        utilisateursLabel = new Label("Utilisateurs : " + reponse.getUtilisateurs());
        utilisateursLabel.setUIID("labelDefault");

        questionLabel = new Label("Question : " + reponse.getQuestion());
        questionLabel.setUIID("labelDefault");

        contenuLabel = new Label("Contenu : " + reponse.getContenu());
        contenuLabel.setUIID("labelDefault");

        dateCreationLabel = new Label("DateCreation : " + new SimpleDateFormat("dd-MM-yyyy").format(reponse.getDateCreation()));
        dateCreationLabel.setUIID("labelDefault");

        utilisateursLabel = new Label("Utilisateurs : " + reponse.getUtilisateurs().getId());
        utilisateursLabel.setUIID("labelDefault");

        questionLabel = new Label("Question : " + reponse.getQuestion().getSujet());
        questionLabel.setUIID("labelDefault");


        reponseModel.addAll(

                utilisateursLabel, questionLabel, contenuLabel, dateCreationLabel
        );

        return reponseModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeReponseModel(Reponse reponse) {

        Container reponseModel = makeModelWithoutButtons(reponse);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentReponse = reponse;
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
                    currentReponse = null;
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