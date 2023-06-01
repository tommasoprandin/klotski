package it.unipd.view;

import it.unipd.models.Match;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.Duration;

public class WinView extends View {

    private VBox container;
    private Label message;
    private Label timeLabel;
    private Label movesLabel;

    public WinView() {
        message = new Label("You have WON!");
        timeLabel = new Label();
        movesLabel = new Label();
        container = new VBox(message, timeLabel, movesLabel);
        this.getChildren().add(container);
        this.setStyle("-fx-font-family: sans-serif");
    }

    @Override
    public void render(Object data) {
        if (!(data instanceof Match)) return;
        Match matchState = (Match)data;
        Duration time = matchState.getElapsedTime();
        long hours = time.toHours();
        long minutes = time.minusHours(hours).toMinutes();
        long seconds = time.minusHours(hours).minusMinutes(minutes).toSeconds();
        timeLabel.setText("Time used: " + String.format("%dH:%dM:%dS", hours, minutes, seconds));
        movesLabel.setText("Moves used: " + matchState.getMoves().size());
    }

}
