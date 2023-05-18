package it.unipd.commands;

import it.unipd.models.Block;
import it.unipd.models.Board;

public class MoveCommand implements Command {

    private final Board board;
    private final Block block;
    private final Block.Direction dir;

    public MoveCommand(Board board, Block.Direction dir) {
        this.board = board;
        this.dir = dir;
        block = board.getSelected();
    }

    @Override
    public void execute() {
        if (block != null) {
            var ok = board.move(block, dir);
            if (ok) board.pushHistory(this);
        }
    }

    public void undo() {
        if (block != null) {
            switch (dir) {
                case U:
                    board.move(block, Block.Direction.D);
                    break;
                case D:
                    board.move(block, Block.Direction.U);
                    break;
                case L:
                    board.move(block, Block.Direction.R);
                    break;
                case R:
                    board.move(block, Block.Direction.L);
                    break;
            }
        }
    }

}
