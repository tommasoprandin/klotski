package it.unipd.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void moveUp() {
        Block block = new Block(1, 1, 1, 2);

        block.move(Block.Direction.U);
        assertEquals(0, block.getY());
    }

    void moveDown() {
        Block block = new Block(1, 1, 1, 2);

        block.move(Block.Direction.D);
        assertEquals(2, block.getY());
    }

    void moveLeft() {
        Block block = new Block(1, 1, 1, 2);

        block.move(Block.Direction.L);
        assertEquals(0, block.getX());
    }

    void moveRight() {
        Block block = new Block(1, 1, 1, 2);

        block.move(Block.Direction.R);
        assertEquals(2, block.getX());
    }

    @Test
    void contains() {
        int x = 1;
        int y = 2;
        Block block = new Block(1, 1, 1, 2);

        assertTrue(block.contains(x, y));
    }

    @Test
    void intersects() {
        Block block1 = new Block(1, 1, 1, 2);
        Block block2 = new Block(1, 2, 2, 1);

        assertTrue(block1.intersects(block2));
    }
}