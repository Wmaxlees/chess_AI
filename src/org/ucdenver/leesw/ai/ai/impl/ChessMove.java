package org.ucdenver.leesw.ai.ai.impl;

import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.pieces.Piece;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessMove implements Move {
    private byte piece;
    private byte state;
    private long start;
    private long target;

    private static final byte CAPTURING_MASK        = (byte)(0b01 << 0 + 0b01 << 1 + 0b01 << 3 + 0b01 << 4);
    private static final byte CAPTURE_ONLY_MASK     = 0b01 << 5;
    private static final byte FREE_SPACE_ONLY_MASK  = 0b01 << 6;

    public ChessMove() {
        this.state = 0b00;
    }

    @Override
    public boolean isCaptureOnly() {
        return (state & CAPTURE_ONLY_MASK) == CAPTURE_ONLY_MASK;
    }

    @Override
    public void setCaptureOnly() {
        this.state |= CAPTURE_ONLY_MASK;
    }

    @Override
    public boolean isFreeSpaceOnly() {
        return (state & FREE_SPACE_ONLY_MASK) == FREE_SPACE_ONLY_MASK;
    }

    @Override
    public void setFreeSpaceOnly() {
        this.state |= FREE_SPACE_ONLY_MASK;
    }

    @Override
    public boolean isCapturing() {
        return (state & CAPTURING_MASK) > 0;
    }

    @Override
    public byte capturePiece() {
        return (byte) ((state & CAPTURING_MASK) >> 4);
    }

    @Override
    public void setCapturing(byte pieceType) {
        this.state |= Piece.getValue(pieceType) << 4;
    }

    @Override
    public long getTargetLocation() {
        return this.target;
    }

    @Override
    public void setTargetLocation(long target) {
        this.target = target;
    }

    @Override
    public long getStartLocation() {
        return this.start;
    }

    @Override
    public void setStartLocation(long start) {
        this.start = start;
    }

    @Override
    public byte getPiece() {
        return this.piece;
    }

    @Override
    public void setPiece(byte piece) {
        this.piece = piece;
    }

    @Override
    public int getAbsScoreChange() {
        int value = 0;

        byte capturePiece = (byte) ((this.state & CAPTURING_MASK) >> 4);
        if (capturePiece > 0) {
            value =  Piece.getValue(capturePiece);
        }

        return value;
    }
}
