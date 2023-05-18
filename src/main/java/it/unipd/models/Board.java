package it.unipd.models;

import it.unipd.commands.MoveCommand;
import it.unipd.view.Observable;
import it.unipd.view.Observer;

import java.util.*;

public class Board implements Observable {

    int rows, cols;

    private final List<Block> blocks;

    private Block selBlock;
    private Block goalBlock;

    private final List<Observer> observers;

    private final Stack<MoveCommand> movesHistory;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.blocks = new ArrayList<>();
        selBlock = null;
        observers = new LinkedList<>();
        movesHistory = new Stack<>();
    }

    public Board(int rows, int cols, Collection<Block> initBlocks) {
        this(rows, cols);
        for (Block b : blocks) {
            add(b);
        }
    }

    public Board(int rows, int cols, Block... initBlocks) {
        this(rows, cols, Arrays.asList(initBlocks));
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
        this.notifyObservers();
    }

    public void addAll(Block... blocks) {
        if (blocks.length < 1) return;
        for (Block b : blocks) {
            this.blocks.add(new Block(b));
        }
        setGoal(blocks[0]);
        this.notifyObservers();
    }


    public void selectBlock(int x, int y) {
        for (Block b : blocks) {
            if (b.getX() == x && b.getY() == y) {
                selBlock = b;
                return;
            }
        }
        this.notifyObservers();
    }

    public void deselectBlock() {
        selBlock = null;
        this.notifyObservers();
    }

    public void clear() {
        this.blocks.clear();
        this.movesHistory.clear();
        this.notifyObservers();
    }

    public Block getSelected() {
        return selBlock;
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
        this.notifyObservers();
        return true;
    }

    public void moveSelected(Block.Direction dir) {
        this.move(selBlock, dir);
    }

    public void undoMove() {
        if (movesHistory.isEmpty()) return;
        movesHistory.pop().undo();
        this.notifyObservers();
    }

    public void pushHistory(MoveCommand cmd) {
        movesHistory.push(cmd);
        this.notifyObservers();
    }

    public int getMovesCount() {
        return movesHistory.size();
    }

    @Override
    public String toString() {
        return "Board{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", blocks=" + blocks +
                ", selBlock=" + selBlock +
                '}';
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    public List<Observer> getObservers() { return observers; }
}
