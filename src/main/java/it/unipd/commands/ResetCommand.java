package it.unipd.commands;

import it.unipd.models.Block;
import it.unipd.models.Board;

public class ResetCommand implements Command{

    private static Block[][] configurations = {
            {
                    new Block(1, 0, 2, 2),
                    new Block(0, 0, 1, 2),
                    new Block(3, 0, 1, 2),
                    new Block(0, 2, 1, 2),
                    new Block(1, 2, 1, 1),
                    new Block(2, 2, 1, 1),
                    new Block(3, 2, 1, 2),
                    new Block(1, 3, 1, 1),
                    new Block(2, 3, 1, 1),
                    new Block(1, 4, 2, 1),
            }
    };

    private Board board;
    private int config;

    public ResetCommand(Board board, int config) {
        this.board = board;
        this.config = config;
    }

    @Override
    public void execute() {
        board.clear();
        board.addAll(configurations[config]);
    }

}
