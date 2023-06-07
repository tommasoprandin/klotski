package it.unipd;

import it.unipd.controllers.MatchController;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;
import it.unipd.view.View;
import it.unipd.view.WinView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    public static int WIDTH = 480;
    public static int HEIGHT = 800;

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
        var pane = new StackPane(newMatchView, matchView, winView);
        pane.setPrefSize(WIDTH, HEIGHT);
        var scene = new Scene(pane, WIDTH, HEIGHT);
        var style  = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(style);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}