package it.unipd.controllers;

import it.unipd.models.*;
import it.unipd.utils.BestMoveFetcher;
import it.unipd.view.View;

import java.net.URI;

public class MatchController extends Controller {
    private Match match;
    private Configuration config;
    private View newMatchView;
    private View matchView;
    private View winView;
    private final BestMoveFetcher bestMoveFetcher;

    private static MatchController instance;

    private MatchController() {
        bestMoveFetcher = new BestMoveFetcher(URI.create("http://localhost:9000/2015-03-31/functions/function/invocations"));
    }
    public static MatchController getInstance() {
        if (instance == null) {
            instance = new MatchController();
            return instance;
        }
        return instance;
    }

    public void setNewMatchView(View nmv) {
        newMatchView = nmv;
    }

    public void setMatchView(View matchView) {
        this.matchView = matchView;
    }

    public void setWinView(View winView) {
        this.winView = winView;
    }

    public void start() {
        newMatchView.show();
        matchView.hide();
        winView.hide();
    }

    public void createNewMatch(Configuration config) {
        match = new Match(new Board(5, 4, config.getBlocks()));
        this.config = config;
        newMatchView.hide();
        matchView.render(match);
        matchView.show();
    }

    public void selectBlock(int x, int y) {
        match.getBoard().selectBlock(x, y);
        matchView.render(match);
    }

    public void moveBlock(Block.Direction dir) {
        Block selectedBlock = match.getBoard().getSelBlock();
        if (selectedBlock == null) return;
        boolean ok = match.getBoard().move(selectedBlock, dir);
        if (ok) {
            match.getMoves().push(new Move(selectedBlock, dir));
            matchView.render(match);
        }
        if (match.getBoard().hasWon()) {
            System.out.println("Won");
            matchView.hide();
            winView.show();
            winView.render(match);
        }
    }

    public void undoMove() {
        if (match.getMoves().empty()) return;
        match.getMoves().pop().undo();
        matchView.render(match);
    }

    public void resetBoard() {
        match.getBoard().reset(config);
        match.getMoves().clear();
        match.resetTimer();
        matchView.render(match);
    }

    public int getMovesCount() {
        if (match != null)
            return match.getMoves().size();
        return 0;
    }

    public void doNextBestMove() {
        try {
            Move bestMove = bestMoveFetcher.getNextBestMove(match.getBoard());
            bestMove.exec();
            match.getMoves().push(bestMove);
            matchView.render(match);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            if (match.getBoard().hasWon()) {
                System.out.println("Won");
                matchView.hide();
                winView.show();
                winView.render(match);
            }
        }
    }

}
