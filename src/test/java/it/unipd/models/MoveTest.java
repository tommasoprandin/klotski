package it.unipd.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void undoUp() {
        Block block = new Block(1, 1, 1, 2);

        Move move = new Move(block, Block.Direction.U);
        move.undo();
        assertNotEquals(0, block.getY());
    }

    @Test
    void undoDown() {
        Block block = new Block(1, 1, 1, 2);

        Move move = new Move(block, Block.Direction.D);
        move.undo();
        assertNotEquals(2, block.getY());
    }

    @Test
    void undoLeft() {
        Block block = new Block(1, 1, 1, 2);

        Move move = new Move(block, Block.Direction.L);
        move.undo();
        assertNotEquals(0, block.getX());
    }

    @Test
    void undoRight() {
        Block block = new Block(1, 1, 1, 2);

        Move move = new Move(block, Block.Direction.R);
        move.undo();
        assertNotEquals(2, block.getX());
    }
}