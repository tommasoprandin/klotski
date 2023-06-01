package it.unipd.view;

import it.unipd.controllers.Controller;
import javafx.scene.layout.Pane;

public abstract class View extends Pane {

    public abstract void render(Object data);
    public void hide() {
        this.setVisible(false);
    }
    public void show() {
        this.setVisible(true);
    }
}
