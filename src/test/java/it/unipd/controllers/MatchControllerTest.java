package it.unipd.controllers;

import it.unipd.models.Block;
import it.unipd.models.Configuration;
import it.unipd.models.Move;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;
import it.unipd.view.WinView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {

    public static class ToolKitInitializer extends Application {

        @Override
        public void start(Stage stage) throws Exception {

        }
    }

    private MatchController matchController;

    @BeforeAll
    public static void initTest() {
        Thread thread = new Thread() {
            public void run() {
                Application.launch(ToolKitInitializer.class, new String[0]);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @BeforeEach
    public void initializeMatchController() {
        matchController = MatchController.getInstance();
        matchController.setNewMatchView(new NewMatchView());
        matchController.setMatchView(new MatchView());
        matchController.setWinView(new WinView());
    }

    @Test
    void start(){
        matchController.start();
        assertTrue(matchController.getNewMatchView().isVisible());
        assertFalse(matchController.getMatchView().isVisible());
        assertFalse(matchController.getWinView().isVisible());

    }

    @Test
    void createNewMatch() {
        matchController.createNewMatch(new Configuration(0));
        assertTrue(matchController.getMatch().getMoves().empty());
        Configuration configuration0 = new Configuration(0);
        Block[] blocks = configuration0.getBlocks();
        List<Block> list = matchController.getMatch().getBoard().getBlocks();

        for (int i = 0; i < 10; i++) {
               assertEquals(blocks[i], list.get(i));
        }

        assertFalse(matchController.getNewMatchView().isVisible());
        assertTrue(matchController.getMatchView().isVisible());
        assertFalse(matchController.getWinView().isVisible());
    }

    @Test
    void moveBlock() {
        matchController.createNewMatch(new Configuration(0));
        matchController.selectBlock(1, 4);
        matchController.moveBlock(Block.Direction.L);

        Move move = new Move(matchController.getMatch().getBoard().getSelBlock(), Block.Direction.L);

        assertTrue(move.equals(matchController.getMatch().getMoves().peek()));
        assertEquals(1, matchController.getMatch().getMoves().size());
        assertEquals(0, move.getBlock().getX());

    }

    @Test
    void undoMove() {
        matchController.createNewMatch(new Configuration(0));
        matchController.selectBlock(1, 4);
        matchController.moveBlock(Block.Direction.L);
        Move move = new Move(matchController.getMatch().getBoard().getSelBlock(), Block.Direction.L);
        matchController.undoMove();
        assertEquals(1, move.getBlock().getX());
        assertEquals(0, matchController.getMatch().getMoves().size());
    }

    @Test
    void getMovesCount() {
        matchController.createNewMatch(new Configuration(0));

        // do some moves
        matchController.selectBlock(1, 4);
        matchController.moveBlock(Block.Direction.L);
        matchController.selectBlock(3, 2);
        matchController.moveBlock(Block.Direction.D);

        assertEquals(2, matchController.getMovesCount());

    }

    @Test
    @Timeout(10)
    void doNextBestMove() {
        matchController.createNewMatch(new Configuration(0));

        matchController.doNextBestMove();

        assertEquals(1, matchController.getMovesCount());
    }
}