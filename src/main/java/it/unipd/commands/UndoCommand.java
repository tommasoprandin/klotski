package it.unipd.commands;

import it.unipd.models.Board;

public class UndoCommand implements Command {
    private final Board board;

    public UndoCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        board.undoMove();
    }
}
