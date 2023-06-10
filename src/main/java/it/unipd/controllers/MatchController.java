package it.unipd.controllers;

import it.unipd.models.*;
import it.unipd.utils.BestMoveFetcher;
import it.unipd.view.View;

import java.io.*;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class MatchController extends Controller {
    private Match match;
    private Configuration config;
    private View newMatchView;
    private View matchView;
    private View winView;
    private final BestMoveFetcher bestMoveFetcher;
    private final File saveFile;

    private static MatchController instance;

    private MatchController() {
        bestMoveFetcher = new BestMoveFetcher(URI.create("https://7yrnyjvu7kvhsj4tqxjfwwevei0plptc.lambda-url.eu-central-1.on.aws/"));
        saveFile = new File(System.getProperty("user.home") + "/klotski-save");
    }
    public static MatchController getInstance() {
        if (instance == null) {
            instance = new MatchController();
            return instance;
        }
        return instance;
    }

    public Match getMatch() {
        return match;
    }

    public Configuration getConfig() {
        return config;
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

    public void createNewMatch(Configuration config) {
        match = new Match(new Board(5, 4, config.getBlocks()));
        this.config = config;
        newMatchView.hide();
        winView.hide();
        matchView.render(match);
        matchView.show();
    }

    private void updateTimer() {
        matchView.render(match);
    }

    public void resumeMatch() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            Match savedMatch = (Match)ois.readObject();
            this.match = savedMatch;
            Configuration savedConfig = (Configuration) ois.readObject();
            this.config = savedConfig;
            if (savedMatch == null && savedConfig == null) return;
            newMatchView.hide();
            matchView.render(match);
            matchView.show();
        }
        catch (Exception ex) {
            System.err.println("Something went wrong...");
        }
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

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
           oos.writeObject(match);
           oos.writeObject(config);
           oos.flush();
        }
        catch (IOException ex) {
            System.err.println("Something went wrong...");
        }
    }
}
