package org.ucdenver.leesw.ai.board;

/**
 * Created by william.lees on 9/10/15.
 */
public class Position {
    private int x, y;

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

    public Position() {}
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
