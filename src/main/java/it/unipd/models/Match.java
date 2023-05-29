package it.unipd.models;

import it.unipd.controllers.Observer;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Match implements Observable {

    private User player;
    private Stack<Move> moves;
    private Instant startTime;
    private Instant endTime;
    private Board board;
    private List<Observer> observers;

    public Match(User player, Board board) {
        this.player = player;
        this.board = board;
        moves = new Stack<>();
        startTime = Instant.now();
        observers = new LinkedList<>();
    }

    public User getPlayer() {
        return player;
    }

    public Stack<Move> getMoves() {
        return moves;
    }

    public Duration getElapsedTime() {
        endTime = Instant.now();
        return Duration.between(startTime, endTime);
    }

    public Board getBoard() {
        return board;
    }

    public void resetTimer() {
        startTime = Instant.now();
        endTime = null;
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public String toString() {
        return "Match{" +
                "player=" + player +
                ", moves=" + moves +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", board=" + board +
                ", observers=" + observers +
                '}';
    }
}
