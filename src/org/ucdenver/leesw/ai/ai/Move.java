package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.Pieces.Piece;

/**
 * Created by william.lees on 9/10/15.
 */
public class Move {

    private int startX, startY, targetX, targetY;
    private Piece piece;

    public Move(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public int getTargetY() {
        return this.targetY;
    }

    public void setTargetY(int y) {
        this.targetY = y;
    }

    public int getTargetX() {
        return this.targetX;
    }

    public void setTargetX(int x) {
        this.targetX = x;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        String result = "";
        if (piece != null)
            result += piece.getSymbol();

        result += "" +
                (char)('\u0060'+this.startX) + this.startY
                + "-"
                + (char)('\u0060'+this.targetX) + this.targetY;

        return result;
    }
}
