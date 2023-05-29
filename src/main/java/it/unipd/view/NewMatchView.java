package it.unipd.view;

import it.unipd.controllers.MatchController;
import it.unipd.models.Configuration;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class NewMatchView extends Pane implements View {
    private final MatchController controller;
    private final VBox container;
    private final ToggleGroup configGrp;
    private final RadioButton[] configBtns;
    private final TextField usernameTxt;
    private final PasswordField passwordTxt;
    private final Button startBtn;

    public NewMatchView() {
        controller = MatchController.getInstance();
        configGrp = new ToggleGroup();
        configBtns = new RadioButton[4];
        for (int i = 0; i < configBtns.length; i++) {
            configBtns[i] = new RadioButton("Configurazione " + (i + 1));
            configBtns[i].setToggleGroup(configGrp);
        }
        configBtns[0].setSelected(true);
        usernameTxt = new TextField("Inserisci lo username");
        passwordTxt = new PasswordField();
        startBtn = new Button("Inizia");
        startBtn.setOnMouseClicked((evt) -> {
            int config = 0;
            for (int i = 0; i < configBtns.length; i++) {
               if (configBtns[i].equals(configGrp.getSelectedToggle())) config = i;
            }
            controller.createNewMatch(new Configuration(config), usernameTxt.getText(), passwordTxt.getText());
        });
        container = new VBox();
        container.getChildren().addAll(configBtns);
        container.getChildren().addAll(usernameTxt, passwordTxt, startBtn);
        this.getChildren().add(container);
        this.setStyle("-fx-font-family: sans-serif");
    }

    @Override
    public void render(Object data) {

    }

    @Override
    public void hide() {
        super.setVisible(false);
    }

    @Override
    public void show() {
        super.setVisible(true);
    }
}
