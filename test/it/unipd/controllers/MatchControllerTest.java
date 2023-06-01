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

    public MatchController initializeMatchController() {
        MatchController matchController = MatchController.getInstance();
        matchController.setNewMatchView( new NewMatchView());
        matchController.setMatchView( new MatchView());
        matchController.setWinView( new WinView());

        return matchController;
    }

    @Test
    void start(){
        MatchController matchController = initializeMatchController();
        matchController.start();

        assertTrue(matchController.getNewMatchView().isVisible());
        assertFalse(matchController.getMatchView().isVisible());
        assertFalse(matchController.getWinView().isVisible());

    }

    @Test
    void createNewMatch() {
        MatchController matchController = initializeMatchController();

        matchController.createNewMatch(new Configuration(0), "Nicola", "1234");
        assertEquals("Nicola", matchController.getMatch().getPlayer().getUsername());
        assertEquals("1234", matchController.getMatch().getPlayer().getPassword());
        assertTrue(matchController.getMatch().getMoves().empty());
        Configuration configuration0 = new Configuration(0);
        Block[] blocks = configuration0.getBlocks();
        List<Block> list = matchController.getMatch().getBoard().getBlocks();

        for (int i = 0; i < 10; i++) {
               assertEquals(blocks[i], list.get(i));
        }

        assertFalse(matchController.getNewMatchView().isVisible());
        assertTrue(matchController.getMatchView().isVisible());
    }

    @Test
    void moveBlock() {
        MatchController matchController = initializeMatchController();

        matchController.createNewMatch(new Configuration(0), "Nicola", "1234");
        matchController.selectBlock(1, 4);
        matchController.moveBlock(Block.Direction.L);

        Move move = new Move(matchController.getSelBlock(), Block.Direction.L);

        assertTrue(move.equals(matchController.getMatch().getMoves().peek()));
        assertEquals(1, matchController.getMatch().getMoves().size());
        assertEquals(0, move.getBlock().getX());

    }

    @Test
    void undoMove() {
        MatchController matchController = initializeMatchController();


        matchController.createNewMatch(new Configuration(0), "Nicola", "1234");
        matchController.selectBlock(1, 4);
        matchController.moveBlock(Block.Direction.L);
        Move move = new Move(matchController.getSelBlock(), Block.Direction.L);

        matchController.undoMove();

        assertEquals(1, move.getBlock().getX());
        assertEquals(0, matchController.getMatch().getMoves().size());
    }

    @Test
    void getMovesCount() {
        MatchController matchController = initializeMatchController();

        matchController.createNewMatch(new Configuration(0), "Nicola", "1234");
        matchController.selectBlock(1, 4);

        // do some moves
        matchController.moveBlock(Block.Direction.L);
        matchController.selectBlock(3, 2);
        matchController.moveBlock(Block.Direction.D);

        assertEquals(2, matchController.getMovesCount());

    }

    @Test
    @Timeout(10)
    void doNextBestMove() {
        MatchController matchController = initializeMatchController();
        matchController.createNewMatch(new Configuration(0), "Nicola", "1234");

        matchController.doNextBestMove();

        assertEquals(1, matchController.getMovesCount());
        assertEquals(1, matchController.getMatch().getMoves().size());

    }
}