package it.unipd.models;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Match {

    private User player;
    private Stack<Move> moves;
    private Instant startTime;
    private Instant endTime;
    private Board board;

    public Match(User player, Board board) {
        this.player = player;
        this.board = board;
        moves = new Stack<>();
        startTime = Instant.now();
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
    public String toString() {
        return "Match{" +
                "player=" + player +
                ", moves=" + moves +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", board=" + board +
                '}';
    }
}
