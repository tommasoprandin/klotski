package it.unipd.commands;

import it.unipd.models.Board;

public class SelectCommand implements Command {

    private final Board board;
    private final int x, y;

    public SelectCommand(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
       board.selectBlock(x, y);
    }

}
