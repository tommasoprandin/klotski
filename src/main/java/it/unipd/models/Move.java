package it.unipd.models;

import java.util.Objects;

public class Move {

    private final Block block;
    private final Block.Direction dir;

    public Move(Block block, Block.Direction dir) {
        this.block = block;
        this.dir = dir;
    }

    public Block getBlock() {
        return block;
    }

    public Block.Direction getDir() {
        return dir;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Objects.equals(block, move.block) && dir == move.dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, dir);
    }
}
