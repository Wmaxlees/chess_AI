package org.ucdenver.leesw.ai.ai;

/**
 * Created by william.lees on 9/15/15.
 */
public interface Move {
    long getTargetLocation();
    void setTargetLocation(long target);

    long getStartLocation();
    void setStartLocation(long start);

    byte getPiece();
    void setPiece(byte piece);
}
