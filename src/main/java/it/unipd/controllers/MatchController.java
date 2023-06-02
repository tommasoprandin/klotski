package it.unipd.controllers;

import it.unipd.models.*;
import it.unipd.view.MatchView;
import it.unipd.view.NewMatchView;
import java.nio.*;
import java.io.*;
import java.util.Stack;
import java.util.*;

public class MatchController implements Observer {
    private Match match;
    private Configuration config;
    private Block selBlock;
    private NewMatchView newMatchView;
    private MatchView matchView;
    private FileWriter moveFile;

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
        try {
            moveFile = new FileWriter("movefile.txt");
            moveFile.write(username + " " + password+"\n");
        }catch(IOException e){
            System.out.println("An error occurred");
        }
    }

    public void resumeMatch(String username, String password){
        try {
            File readFile =new File ("movefile.txt");
            Scanner in=new Scanner(readFile);
            in.nextLine();
            List<Block> blocks=new ArrayList<>();
            for(int i=0;i<10;i++){
                int x=Integer.parseInt(in.next());
                int y=Integer.parseInt(in.next());
                int w=Integer.parseInt(in.next());
                int h=Integer.parseInt(in.next());
                blocks.add(new Block(x,y,w,h));
            }
            Block[] blockArray=new Block[blocks.size()];
            int goal=0;
            for(int i=0;i<blocks.size();i++){
                blockArray[i]=blocks.get(i);
                if(blocks.get(i).getH()==2 && blocks.get(i).getW()==2) goal=i;
            }
            Configuration config=new Configuration(blockArray);

            match = new Match(new User(username, password), new Board(5, 4, goal, config.getBlocks()));
            this.config = config;
            newMatchView.hide();
            matchView.render(match);
            matchView.show();
            in.close();
        }catch (IOException e){
            System.out.println("An error occurred");
        }
    }

    public void selectBlock(int x, int y) {
        selBlock = match.getBoard().getBlock(x, y);
    }

    public void moveBlock(Block.Direction dir) {

        boolean ok = match.getBoard().move(selBlock, dir);
        if(ok){
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

    public void save(){
        try{
           List<Block> state =match.getBoard().getBlocks();
           for (int i=0;i<state.size();i++){
               moveFile.write(state.get(i).toString());
           }
           moveFile.close();

        }catch(IOException e){
            System.out.println("An error occurred");
        }

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
