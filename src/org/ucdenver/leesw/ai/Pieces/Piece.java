package org.ucdenver.leesw.ai.Pieces;

/**
 * Created by william.lees on 9/10/15.
 */
public abstract class Piece {

    protected Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract String getSymbol();
    public abstract int getValue();
}
