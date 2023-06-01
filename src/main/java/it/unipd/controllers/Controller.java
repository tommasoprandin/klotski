package it.unipd.controllers;

public class Controller {

    static Controller instance;
    static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
            return instance;
        }
        return instance;
    }
}
