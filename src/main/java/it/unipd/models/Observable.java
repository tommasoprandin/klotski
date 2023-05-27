package it.unipd.models;

import it.unipd.controllers.Observer;

public interface Observable {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers();
}
