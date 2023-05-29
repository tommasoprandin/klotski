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

    private MatchController() {
    }
    public static MatchController getInstance() {
        if (instance == null) {
            instance = new MatchController();
            return instance;
        }
        return instance;
    }

    public NewMatchView getNewMatchView() {
        return newMatchView;
    }

    public MatchView getMatchView() {
        return matchView;
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
        match = new Match(new User(username, password), new Board(5, 4, config.getGoalBlockIdx(), config.getBlocks()));
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
    }

    public void undoMove() {
        if (match.getMoves().empty()) return;
        match.getMoves().pop().undo();
        matchView.render(match);
    }

    public void resetBoard() {
        match.getBoard().clear();
        match.getBoard().addAll(config.getBlocks());
        match.getMoves().clear();
        match.resetTimer();
        matchView.render(match);
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
