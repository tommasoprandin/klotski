package it.unipd.view;

public interface Observable {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers();
}
