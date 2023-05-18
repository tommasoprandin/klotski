package it.unipd;

import it.unipd.models.Board;
import it.unipd.view.BoardView;
import javafx.application.Application;
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

        Board board = new Board(5, 4);
        BoardView view = new BoardView(board);
        board.register(view);

        stage.setScene(view.getScene());
        stage.show();
    }

}