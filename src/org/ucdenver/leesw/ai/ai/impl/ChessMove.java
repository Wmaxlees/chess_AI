package org.ucdenver.leesw.ai.ai.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.BitUtilities;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.pieces.Piece;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessMove implements Move {

    private static Logger logger = LogManager.getLogger(ChessMove.class);

    private byte piece;
    private long start;
    private long target;

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
    public String toString() {
        return Piece.getSymbol(this.piece)  +
                BitUtilities.generateXChar(this.start)  + BitUtilities.generateYChar(this.start) + "-" +
                BitUtilities.generateXChar(this.target) + BitUtilities.generateYChar(this.target);
    }
}
