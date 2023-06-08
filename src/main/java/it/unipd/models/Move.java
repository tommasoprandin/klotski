package it.unipd.models;

import java.io.Serializable;

public class Move implements Serializable {

    private final Block block;
    private final Block.Direction dir;

    public Move(Block block, Block.Direction dir) {
        this.block = block;
        this.dir = dir;
    }

    public void exec() {
        block.move(dir);
    }

    public void undo() {
        switch (dir) {
            case U: block.move(Block.Direction.D); break;
            case D: block.move(Block.Direction.U); break;
            case R: block.move(Block.Direction.L); break;
            case L: block.move(Block.Direction.R); break;
        }
    }
}
