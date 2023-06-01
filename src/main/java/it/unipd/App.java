package it.unipd;

import it.unipd.controllers.MatchController;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;
import it.unipd.view.View;
import it.unipd.view.WinView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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

        View newMatchView = new NewMatchView();
        View matchView = new MatchView();
        View winView = new WinView();
        MatchController controller = MatchController.getInstance();
        controller.setNewMatchView(newMatchView);
        controller.setMatchView(matchView);
        controller.setWinView(winView);
        controller.start();

        var scene = new Scene(new StackPane(newMatchView, matchView, winView), 800, 1000);
        stage.setScene(scene);
        stage.show();
    }

}