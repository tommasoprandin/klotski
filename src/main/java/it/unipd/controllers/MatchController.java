package it.unipd.controllers;

import it.unipd.models.*;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;

public class MatchController implements Observer {
    private Match match;
    private Configuration config;
    private Block selBlock;
    private NewMatchView newMatchView;
    private MatchView matchView;

    private static MatchController instance;

    private MatchController() {}
    public static MatchController getInstance() {
        if (instance == null) {
            instance = new MatchController();
            return instance;
        }
        return instance;
    }
    public void setNewMatchView(NewMatchView nmv) {
        newMatchView = nmv;
    }

    public void setMatchView(MatchView matchView) {
        this.matchView = matchView;
    }

    public void start() {
        newMatchView.show();
        matchView.hide();
    }

    public void createNewMatch(Configuration config, String username, String password) {
        System.out.println(config);
        match = new Match(new User(username, password), new Board(5, 4, config.getBlocks()));
        this.config = config;
        newMatchView.hide();
        matchView.show();
        System.out.println(match);
        matchView.render(match);
    }

    public void selectBlock(int x, int y) {
        selBlock = match.getBoard().getBlock(x, y);
    }

    public void moveBlock(Block.Direction dir) {
        boolean ok = match.getBoard().move(selBlock, dir);
        if (ok) {
            match.pushMove(new Move(selBlock, dir));
            matchView.render(match);
        }
    }

    public void undoMove() {
        Move move = match.popMove();
        move.undo();
    }

    public void resetBoard() {
        match.getBoard().clear();
        match.getBoard().addAll(config.getBlocks());
        match.getMoves().clear();
        match.resetTimer();
    }

    public int getMovesCount() {
        if (match != null)
            return match.getMoves().size();
        return 0;
    }
    @Override
    public void update() {

    }
}
