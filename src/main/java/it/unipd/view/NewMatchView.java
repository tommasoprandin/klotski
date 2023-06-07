package it.unipd.view;

import it.unipd.App;
import it.unipd.controllers.MatchController;
import it.unipd.models.Configuration;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class NewMatchView extends View {
    private final MatchController controller;
    private final VBox rootContainer;
    private final FlowPane configContainer;
    private final ToggleGroup configGrp;
    private final RadioButton[] configBtns;
    private final Label configLbl;
    private final Button startBtn;
    private final Button resumeBtn;

    public NewMatchView() {
        controller = MatchController.getInstance();
        configLbl = new Label("Seleziona una configurazione!");
        configLbl.getStyleClass().add("config-label");
        configGrp = new ToggleGroup();
        configBtns = new RadioButton[Configuration.getAvailableConfigs()];
        configContainer = new FlowPane();
        configContainer.setPrefWrapLength(App.WIDTH);
        configContainer.getStyleClass().add("config-grid");
        for (int i = 0; i < configBtns.length; i++) {
            configBtns[i] = new RadioButton(Integer.toString(i + 1));
            configBtns[i].setToggleGroup(configGrp);
            configBtns[i].setPrefSize(App.WIDTH / 2, App.WIDTH / 2);
            configBtns[i].getStyleClass().remove("radio-button");
            configBtns[i].getStyleClass().add("toggle-button");
            configContainer.getChildren().add(configBtns[i]);
        }
        configBtns[0].setSelected(true);
        startBtn = new Button("Inizia!");
        startBtn.getStyleClass().add("start-btn");
        startBtn.setOnMouseClicked((evt) -> {
            int config = 0;
            for (int i = 0; i < configBtns.length; i++) {
               if (configBtns[i].equals(configGrp.getSelectedToggle())) config = i;
            }
            controller.createNewMatch(new Configuration(config));
        });
        resumeBtn = new Button("Riprendi");
        resumeBtn.getStyleClass().add("resume-btn");
        rootContainer = new VBox();
        rootContainer.setPrefSize(App.WIDTH, App.HEIGHT);
        rootContainer.getStyleClass().add("root-container");
        rootContainer.getChildren().add(configLbl);
        rootContainer.getChildren().add(configContainer);
        rootContainer.getChildren().add(startBtn);
        rootContainer.getChildren().add(resumeBtn);
        this.getChildren().add(rootContainer);
    }

    @Override
    public void render(Object data) {

    }
}
