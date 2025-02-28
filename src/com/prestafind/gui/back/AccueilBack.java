package com.prestafind.gui.back;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.prestafind.gui.Login;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;
    Form previous;
    public static Form accueilForm;

    public AccueilBack(Form previous) {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        accueilForm = this;
        addGUIs();
    }

    private void addGUIs() {
        label = new Label("Espace administrateur"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeQuestionsButton(),
                makeReponsesButton()
        );

        this.add(menuContainer);
    }

    private Button makeQuestionsButton() {
        Button button = new Button("Questions");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_QUESTION_ANSWER);
        button.addActionListener(action -> new com.prestafind.gui.back.question.AfficherToutQuestion(this).show());
        return button;
    }

    private Button makeReponsesButton() {
        Button button = new Button("Reponses");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_MESSAGE);
        button.addActionListener(action -> new com.prestafind.gui.back.reponse.AfficherToutReponse(this).show());
        return button;
    }
}
