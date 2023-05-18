package it.unipd.commands;

public interface Invoker {
    void add(Command cmd);
    void execute();
    void undo();
}
