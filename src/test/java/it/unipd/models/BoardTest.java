package it.unipd.models;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.html.BlockView;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void add() {
        Block block = new Block(1, 1, 2, 1);
        Board board = new Board(5, 4);

        board.add(block);
        assertTrue(board.getBlocks().size() == 1);
    }

    @Test
    void addAll() {
        Configuration configuration = new Configuration(0);
        Board board = new Board(5, 4);

        board.addAll(configuration.getBlocks());
        assertTrue(board.getBlocks().size() == 10);
    }

    @Test
    void reset() {
        Configuration configuration1 = new Configuration(0);
        Configuration configuration2 = new Configuration(1);
        Block[] blocks = configuration2.getBlocks();
        Board board = new Board(5, 4, configuration1.getBlocks());

        board.reset(configuration2);
        List<Block> list = board.getBlocks();

        for (int i = 0; i < 10; i++){
            assertEquals(blocks[i], list.get(i));
        }
    }

    @Test
    void clear(){
        Board board = new Board(5, 4, new Configuration(0).getBlocks());

        board.clear();
        assertEquals(0, board.getBlocks().size());
    }

    @Test
    void movePermitted() {
        Board board = new Board(5, 4,new Configuration(0).getBlocks());
        Block target = board.getBlock(1, 4);
        board.move(target, Block.Direction.L);

        assertEquals(0, target.getX());
    }

    @Test
    void moveNotPermitted() {
        Board board = new Board(5, 4,new Configuration(0).getBlocks());
        Block target = board.getBlock(1, 4);
        board.move(target, Block.Direction.D);

        assertNotEquals(5, target.getY());
    }
}