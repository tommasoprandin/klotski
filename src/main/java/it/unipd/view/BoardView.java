package it.unipd.view;

import it.unipd.commands.*;
import it.unipd.models.Block;
import it.unipd.models.Board;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class BoardView implements Observer {

    private VBox container;
    private GridPane gp;
    private Button reset;
    private Button undo;
    private Label movesCounter;
    private Scene scene;

    public BoardView(Board initBoard) {
        gp = new GridPane();
        for (int i = 0; i < initBoard.getCols(); i++) {
            gp.getColumnConstraints().add(new ColumnConstraints(100));
        }
        for (int i = 0; i < initBoard.getRows(); i++) {
            gp.getRowConstraints().add(new RowConstraints(100));
        }
        gp.setVgap(10);
        gp.setHgap(10);
        reset = new Button("Reset");
        reset.setOnMouseClicked((evt) -> {
            var cmd = new ResetCommand(initBoard, 0);
            cmd.execute();
        });
        undo = new Button("Undo");
        undo.setOnMouseClicked((evt) -> {
            var cmd = new UndoCommand(initBoard);
            cmd.execute();
        });
        movesCounter = new Label(Integer.toString(initBoard.getMovesCount()));
        container = new VBox(gp, reset, undo, movesCounter);
        container.setStyle("-fx-font-family: sans-serif");

        scene = new Scene(container, 800, 800);
        scene.setOnKeyPressed((evt) -> {
            System.out.println("Key pressed: " + evt.getText());
            MoveCommand cmd = null;
            switch (evt.getText()) {
                case "w": {
                    cmd = new MoveCommand(initBoard, Block.Direction.U);
                    break;
                }
                case "a": {
                    cmd = new MoveCommand(initBoard, Block.Direction.L);
                    break;
                }
                case "s": {
                    cmd = new MoveCommand(initBoard, Block.Direction.D);
                    break;
                }
                case "d": {
                    cmd = new MoveCommand(initBoard, Block.Direction.R);
                    break;
                }
            }
            if (cmd != null)
                cmd.execute();
        });
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(Object ctx) {
        if (!(ctx instanceof Board)) return;
        Board board = (Board)ctx;
        gp.getChildren().clear();
        for (Block b : board.getBlocks()) {
            Button button = new Button();
            button.setOnMouseClicked((evt) -> {
                var cmd = new SelectCommand(board, b.getX(), b.getY());
                cmd.execute();
            });
            if (b.equals(board.getGoal()))
                button.setStyle("-fx-background-color: crimson");
            else
                button.setStyle("-fx-background-color: burlywood");
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            gp.add(button, b.getX(), b.getY(), b.getW(), b.getH());
        }
        movesCounter.setText(Integer.toString(board.getMovesCount()));
    }

}
