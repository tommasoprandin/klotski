package it.unipd;

import it.unipd.controllers.MatchController;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        NewMatchView newMatchView = new NewMatchView();
        MatchView matchView = new MatchView();
        MatchController controller = MatchController.getInstance();
        controller.setNewMatchView(newMatchView);
        controller.setMatchView(matchView);
        controller.start();

        var scene = new Scene(new StackPane(newMatchView, matchView), 800, 1000);
        stage.setScene(scene);
        stage.show();
    }

}