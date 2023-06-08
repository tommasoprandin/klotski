package it.unipd.view;

import it.unipd.App;
import it.unipd.controllers.MatchController;
import it.unipd.models.Block;
import it.unipd.models.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MatchView extends View {

    MatchController controller;
    private VBox rootContainer;
    private GridPane boardPane;
    private HBox controlsContainer;
    private Button backBtn;
    private Button resetBtn;
    private Button undoBtn;
    private Button bestMoveBtn;
    private Button saveBtn;
    private Label movesCounter;

    public MatchView() {
        controller = MatchController.getInstance();
        boardPane = new GridPane();
        boardPane.setPrefWidth(App.WIDTH);
        boardPane.getStyleClass().add("board");
        backBtn = new Button();
        backBtn.setPrefSize(32, 32);
        backBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/left-arrow.png"))));
        backBtn.setOnMouseClicked((evt) -> {
            controller.start();
        });
        resetBtn = new Button();
        resetBtn.setPrefSize(32, 32);
        resetBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/refresh.png"))));
        resetBtn.setOnMouseClicked((evt) -> {
           controller.resetBoard();
        });
        undoBtn = new Button();
        undoBtn.setPrefSize(32, 32);
        undoBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/undo.png"))));
        undoBtn.setOnMouseClicked((evt) -> {
            controller.undoMove();
        });
        bestMoveBtn = new Button();
        bestMoveBtn.setPrefSize(32, 32);
        bestMoveBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/star.png"))));
        bestMoveBtn.setOnMouseClicked((evt) -> {
            controller.doNextBestMove();
        });
        saveBtn = new Button();
        saveBtn.setPrefSize(32, 32);
        saveBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/save.png"))));
        saveBtn.setOnMouseClicked((evt) -> {
            controller.save();
        });
        movesCounter = new Label(Integer.toString(controller.getMovesCount()));
        movesCounter.getStyleClass().add("moves-counter");
        controlsContainer = new HBox(backBtn, resetBtn, undoBtn, bestMoveBtn, saveBtn);
        controlsContainer.getStyleClass().add("controls-container");
        rootContainer = new VBox(boardPane, controlsContainer, movesCounter);
        VBox.setVgrow(movesCounter, Priority.ALWAYS);
        rootContainer.getStyleClass().add("root-container");
        rootContainer.setPrefSize(App.WIDTH, App.HEIGHT);
        this.getChildren().add(rootContainer);

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
        boardPane.getColumnConstraints().clear();
        boardPane.getRowConstraints().clear();
        for (int i = 0; i < matchState.getBoard().getCols(); i++) {
            boardPane.getColumnConstraints().add(new ColumnConstraints(App.WIDTH / matchState.getBoard().getCols()));
        }
        for (int i = 0; i < matchState.getBoard().getRows(); i++) {
            boardPane.getRowConstraints().add(new RowConstraints(App.WIDTH / matchState.getBoard().getCols()));
        }
        boardPane.getChildren().clear();
        for (var block : matchState.getBoard().getBlocks()) {
            Button b = new Button();
            b.getStyleClass().add("block");
            b.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
            if (block.equals(matchState.getBoard().getGoal()))
                b.getStyleClass().add("goal");
            if (block.equals(matchState.getBoard().getSelBlock()))
                b.getStyleClass().add("selected");
            b.setOnMouseClicked((evt) -> {
               controller.selectBlock(block.getX(), block.getY());
            });
            boardPane.add(b, block.getX(), block.getY(), block.getW(), block.getH());
        }
        // render score
        movesCounter.setText(Integer.toString(controller.getMovesCount()));
    }
}
