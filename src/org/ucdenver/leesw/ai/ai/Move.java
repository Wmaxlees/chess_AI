package org.ucdenver.leesw.ai.ai;

/**
 * Created by william.lees on 9/15/15.
 */
public interface Move {
    boolean isCaptureOnly();
    void setCaptureOnly();

    boolean isFreeSpaceOnly();
    void setFreeSpaceOnly();
    
    boolean isCapturing();
    byte capturePiece();
    void setCapturing(byte pieceType);

    long getTargetLocation();
    void setTargetLocation(long target);

    long getStartLocation();
    void setStartLocation(long start);

    byte getPiece();
    void setPiece(byte piece);

    int getAbsScoreChange();
}
