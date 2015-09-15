package org.ucdenver.leesw.ai.ai.impl;

/**
 * Created by william.lees on 9/10/15.
 */
public class MoveOld {

    private int startX, startY, targetX, targetY;
    private byte piece;
    private byte state;

    private static final byte CAPTURE_ONLY_MASK = 1;
    private static final byte NOT_CAPTURE_ONLY_MASK = Byte.MAX_VALUE - 1;
    private static final byte FREE_SPACE_ONLY_MASK = 2;
    private static final byte NOT_FREE_SPACE_ONLY_MASK = Byte.MAX_VALUE - 2;

    public boolean isCaptureOnly() {
        return (state & CAPTURE_ONLY_MASK) == CAPTURE_ONLY_MASK;
    }

    public void setCaptureOnly(boolean captureOnly) {
        if (captureOnly) {
            this.state |= CAPTURE_ONLY_MASK;
        } else {
            this.state &= NOT_CAPTURE_ONLY_MASK;
        }
    }

    public boolean isFreeSpaceOnly() {
        return (state & FREE_SPACE_ONLY_MASK) == FREE_SPACE_ONLY_MASK;
    }

    public void setFreeSpaceOnly(boolean freeSpaceOnly) {
        if (freeSpaceOnly) {
            this.state |= FREE_SPACE_ONLY_MASK;
        } else {
            this.state &= NOT_FREE_SPACE_ONLY_MASK;
        }
    }

    public MoveOld(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;

        this.state = 0;
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

    public byte getPiece() {
        return piece;
    }

    public void setPiece(byte piece) {
        this.piece = piece;
    }

//    @Override
//    public String toString() {
//        String result = "";
//        if (piece != null)
//            result += Piece.getSymbol(piece);
//
//        result += "" +
//                (char)('\u0060'+this.startX) + this.startY
//                + "-"
//                + (char)('\u0060'+this.targetX) + this.targetY;
//
//        return result;
//    }
}
