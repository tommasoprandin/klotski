package it.unipd.models;

import java.util.*;

public class Board {

    int rows, cols;
    private final List<Block> blocks;
    private Block goalBlock;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.blocks = new ArrayList<>();
    }

    public Board(int rows, int cols, Collection<Block> initBlocks) {
        this(rows, cols);
        for (Block b : blocks) {
            add(new Block(b));
        }
    }

    public Board(int rows, int cols, Block... initBlocks) {
        this(rows, cols);
        this.addAll(initBlocks);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public final List<Block> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public Block getBlock(int x, int y) {
        for (Block b : blocks) {
            if (b.getX() == x && b.getY() == y) return b;
        }
        return null;
    }

    public void add(Block block) {
        for (Block b : blocks) {
            if (b.intersects(block)) return;
        }
        blocks.add(block);
    }

    public void addAll(Block... blocks) {
        if (blocks.length < 1) return;
        for (Block b : blocks) {
            this.blocks.add(new Block(b));
        }
    }
    public void clear() {
        this.blocks.clear();
    }

    public Block getGoal() {
        return goalBlock;
    }

    public void setGoal(Block b) {
        goalBlock = b;
    }

    public boolean move(Block b, Block.Direction dir) {
        Block test = new Block(b);
        test.move(dir);
        if (test.getX() < 0 || test.getX() + test.getW() > cols || test.getY() < 0 || test.getY() + test.getH() > rows)
            return false;
        for (Block block : blocks) {
            if (!block.equals(b) && block.intersects(test)) return false;
        }
        b.move(dir);
        return true;
    }

    @Override
    public String toString() {
        return "Board{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", blocks=" + blocks +
                ", goalBlock=" + goalBlock +
                '}';
    }
}
