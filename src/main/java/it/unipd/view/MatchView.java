package it.unipd.view;

import it.unipd.controllers.MatchController;
import it.unipd.models.Block;
import it.unipd.models.Match;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MatchView extends Pane implements View {

    MatchController controller;
    private VBox container;
    private GridPane gp;
    private Button reset;
    private Button undo;
    private Label movesCounter;

    public MatchView() {
        controller = MatchController.getInstance();
        gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(10);
        gp.setHgap(10);
        reset = new Button("Reset");
        reset.setOnMouseClicked((evt) -> {
           controller.resetBoard();
        });
        undo = new Button("Undo");
        undo.setOnMouseClicked((evt) -> {
            controller.undoMove();
        });
        movesCounter = new Label(Integer.toString(controller.getMovesCount()));
        container = new VBox(gp, reset, undo, movesCounter);
        container.setStyle("-fx-font-family: sans-serif");
        this.getChildren().add(container);
        this.setStyle("-fx-font-family: sans-serif");

        this.setOnKeyPressed((evt) -> {
            switch (evt.getText()) {
                case "w": {
                    controller.moveBlock(Block.Direction.U);
                    break;
                }
                case "a": {
                    controller.moveBlock(Block.Direction.L);
                    break;
                }
                case "s": {
                    controller.moveBlock(Block.Direction.D);
                    break;
                }
                case "d": {
                    controller.moveBlock(Block.Direction.R);
                    break;
                }
            }
        });
    }

    @Override
    public void render(Object data) {
        if (!(data instanceof Match)) return;
        var matchState = (Match)data;
        // render board
        gp.getColumnConstraints().clear();
        gp.getRowConstraints().clear();
        for (int i = 0; i < matchState.getBoard().getCols(); i++) {
            gp.getColumnConstraints().add(new ColumnConstraints(100));
        }
        for (int i = 0; i < matchState.getBoard().getRows(); i++) {
            gp.getRowConstraints().add(new RowConstraints(100));
        }
        gp.getChildren().clear();
        for (var block : matchState.getBoard().getBlocks()) {
            Button b = new Button();
            b.setMaxWidth(Double.MAX_VALUE);
            b.setMaxHeight(Double.MAX_VALUE);
            b.setStyle((block.equals(matchState.getBoard().getGoal()) ? "-fx-background-color: red" : "-fx-background-color: blue"));
            b.setOnMouseClicked((evt) -> {
               controller.selectBlock(block.getX(), block.getY());
            });
            gp.add(b, block.getX(), block.getY(), block.getW(), block.getH());
        }
        // render score
        movesCounter.setText(Integer.toString(controller.getMovesCount()));
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
