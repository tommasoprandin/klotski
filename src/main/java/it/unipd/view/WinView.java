package it.unipd.view;

import it.unipd.App;
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
        message = new Label("Hai Vinto!!!");
        message.getStyleClass().add("win-message");
        movesLabel = new Label();
        movesLabel.getStyleClass().add("win-moves");
        timeLabel = new Label();
        timeLabel.getStyleClass().add("win-time");
        container = new VBox(message, movesLabel, timeLabel);
        container.getStyleClass().add("root-container");
        container.setPrefSize(App.WIDTH, App.HEIGHT);
        this.getChildren().add(container);
    }

    @Override
    public void render(Object data) {
        if (!(data instanceof Match)) return;
        Match matchState = (Match)data;
        Duration time = matchState.getElapsedTime();
        long hours = time.toHours();
        long minutes = time.minusHours(hours).toMinutes();
        long seconds = time.minusHours(hours).minusMinutes(minutes).toSeconds();
        timeLabel.setText(String.format("Tempo impiegato: %02d:%02d:%02d", hours, minutes, seconds));
        movesLabel.setText("Mosse: " + matchState.getMoves().size());
    }

}
