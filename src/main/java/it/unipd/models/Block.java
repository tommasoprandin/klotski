package it.unipd.models;

import java.io.Serializable;
import java.util.Objects;

public class Block implements Serializable {

    private int x, y, w, h;

    public Block(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Block(Block clone) {
        this(clone.x, clone.y, clone.w, clone.h);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public enum Direction { U, D, L, R }

    public void move(Direction dir) {
        switch (dir) {
            case U: y--; break;
            case D: y++; break;
            case L: x--; break;
            case R: x++; break;
        }
    }

    public boolean contains(int x, int y) {
        return (this.x <= x && x <= this.x + w) && (this.y <= y && y <= this.y + h);
    }

    public boolean intersects(Block other) {
        return (other.x < this.x + this.w && other.x + other.w > this.x && other.y < this.y + this.h && other.y + other.h > this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return x == block.x && y == block.y && w == block.w && h == block.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, h);
    }

    @Override
    public String toString() {
        return "Block{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
