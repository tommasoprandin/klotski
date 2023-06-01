package it.unipd.controllers;

import it.unipd.models.*;
import it.unipd.utils.BestMoveFetcher;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;
import it.unipd.view.View;

import java.net.URI;

public class MatchController extends Controller {
    private Match match;
    private Configuration config;
    private Block selBlock;
    private View newMatchView;
    private View matchView;
    private View winView;
    private BestMoveFetcher bestMoveFetcher;

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

    public View getNewMatchView() {
        return newMatchView;
    }

    public View getMatchView() {
        return matchView;
    }

    public View getWinView() {
        return winView;
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

    public void createNewMatch(Configuration config, String username, String password) {
        match = new Match(new User(username, password), new Board(5, 4, config.getBlocks()));
        this.config = config;
        newMatchView.hide();
        matchView.render(match);
        matchView.show();
    }

    public void selectBlock(int x, int y) {
        selBlock = match.getBoard().getBlock(x, y);
    }

    public void moveBlock(Block.Direction dir) {
        boolean ok = match.getBoard().move(selBlock, dir);
        if (ok) {
            match.getMoves().push(new Move(selBlock, dir));
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
            System.err.println(e);
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
