package it.unipd.view;

import it.unipd.App;
import it.unipd.controllers.MatchController;
import it.unipd.models.Block;
import it.unipd.models.Match;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class MatchView extends View {

    private class MatchTimerTask extends TimerTask {
        Instant start;
        public MatchTimerTask() {
            this.start = Instant.now();
        }
        @Override
        public void run() {
            Duration time = Duration.between(start, Instant.now());
            long hours = time.toHours();
            long minutes = time.minusHours(hours).toMinutes();
            long secs = time.minusHours(hours).minusMinutes(minutes).toSeconds();
            Platform.runLater(() -> {
                timeCounter.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
            });
        }
    }

    MatchController controller;
    private VBox rootContainer;
    private GridPane boardPane;
    private HBox controlsContainer;
    private HBox scoreContainer;
    private Button backBtn;
    private Button resetBtn;
    private Button undoBtn;
    private Button bestMoveBtn;
    private Button saveBtn;
    private Label movesCounter;
    private Region padding;
    private Label timeCounter;
    private final Timer timer;
    private MatchTimerTask timerTask;

    public MatchView() {
        controller = MatchController.getInstance();
        timer = new Timer();
        timerTask = new MatchTimerTask();
        timer.schedule(timerTask, 0, 1000);
        boardPane = new GridPane();
        boardPane.setPrefWidth(App.WIDTH);
        boardPane.getStyleClass().add("board");
        backBtn = new Button();
        backBtn.setPrefSize(32, 32);
        backBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/left-arrow.png"))));
        backBtn.setOnMouseClicked((evt) -> {
            timerTask.cancel();
            timerTask = new MatchTimerTask();
            timer.schedule(timerTask, 0, 1000);
            controller.start();
        });
        resetBtn = new Button();
        resetBtn.setPrefSize(32, 32);
        resetBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("/images/refresh.png"))));
        resetBtn.setOnMouseClicked((evt) -> {
            timerTask.cancel();
            timerTask = new MatchTimerTask();
            timer.schedule(timerTask, 0, 1000);
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
        timeCounter = new Label("00:00:00");
        timeCounter.getStyleClass().add("time-counter");
        padding = new Region();
        controlsContainer = new HBox(backBtn, resetBtn, undoBtn, bestMoveBtn, saveBtn);
        controlsContainer.getStyleClass().add("controls-container");
        scoreContainer = new HBox(movesCounter, padding, timeCounter);
        HBox.setHgrow(padding, Priority.ALWAYS);
        scoreContainer.getStyleClass().add("score-container");
        rootContainer = new VBox(boardPane, controlsContainer, scoreContainer);
        VBox.setVgrow(scoreContainer, Priority.ALWAYS);
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
