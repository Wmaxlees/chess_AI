package org.ucdenver.leesw.ai.Pieces;

/**
 * Created by william.lees on 9/10/15.
 */
public abstract class Piece {
    // Location
//    private int x;
//    private int y;

    // Color
    protected Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }

    public abstract String getSymbol();
    public abstract int getValue();
}
