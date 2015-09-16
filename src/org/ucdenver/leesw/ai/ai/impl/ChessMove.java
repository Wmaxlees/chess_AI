package org.ucdenver.leesw.ai.ai.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.board.BitBoardLayer;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoard;
import org.ucdenver.leesw.ai.pieces.Piece;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessMove implements Move {

    private static Logger logger = LogManager.getLogger(ChessMove.class);

    private byte piece;
    private byte state;
    private long start;
    private long target;

    private static final byte CAPTURING_MASK        = (byte)((0b01 << 0) + (0b01 << 1) + (0b01 << 2) + (0b01 << 3) + (0b01 << 4));
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
        return (byte) (state & CAPTURING_MASK);
    }

    @Override
    public void setCapturing(byte pieceType) {
        this.state |= pieceType;
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

        byte capturePiece = (byte) ((this.state & CAPTURING_MASK));
        if (capturePiece > 0) {
            value =  Piece.getValue(capturePiece);
        }

        return value;
    }

    @Override
    public String toString() {
        return Piece.getSymbol(this.piece)  +
                this.generateX(this.start)  + this.generateY(this.start) + "-" +
                this.generateX(this.target) + this.generateY(this.target);
    }

    private String generateX(long mask) {

        int x = Long.numberOfTrailingZeros(mask) % 8;

        switch (x) {
            case 0: return "a";
            case 1: return "b";
            case 2: return "c";
            case 3: return "d";
            case 4: return "e";
            case 5: return "f";
            case 6: return "g";
            case 7: return "h";
            default:
                logger.error("Trailing Zeros exceeds 7 for generateX: {}", x);
                return "z";
        }
    }

    // TODO: MOVE THIS TO EXTERNAL UTILITY CLASS
    private static long FIRST_ROW_MASK      = (0b01L <<  0) + (0b01L <<  1) + (0b01L <<  2) + (0b01L <<  3) + (0b01L <<  4) + (0b01L <<  5) + (0b01L <<  6) + (0b01L <<  7);
    private static long SECOND_ROW_MASK     = (0b01L <<  8) + (0b01L <<  9) + (0b01L << 10) + (0b01L << 11) + (0b01L << 12) + (0b01L << 13) + (0b01L << 14) + (0b01L << 15);
    private static long THIRD_ROW_MASK      = (0b01L << 16) + (0b01L << 17) + (0b01L << 18) + (0b01L << 19) + (0b01L << 20) + (0b01L << 21) + (0b01L << 22) + (0b01L << 23);
    private static long FORTH_ROW_MASK      = (0b01L << 24) + (0b01L << 25) + (0b01L << 26) + (0b01L << 27) + (0b01L << 28) + (0b01L << 29) + (0b01L << 30) + (0b01L << 31);
    private static long FIFTH_ROW_MASK      = (0b01L << 32) + (0b01L << 33) + (0b01L << 34) + (0b01L << 35) + (0b01L << 36) + (0b01L << 37) + (0b01L << 38) + (0b01L << 39);
    private static long SIXTH_ROW_MASK      = (0b01L << 40) + (0b01L << 41) + (0b01L << 42) + (0b01L << 43) + (0b01L << 44) + (0b01L << 45) + (0b01L << 46) + (0b01L << 47);
    private static long SEVENTH_ROW_MASK    = (0b01L << 48) + (0b01L << 49) + (0b01L << 50) + (0b01L << 51) + (0b01L << 52) + (0b01L << 53) + (0b01L << 54) + (0b01L << 55);
    private static long EIGHTH_ROW_MASK     = (0b01L << 56) + (0b01L << 57) + (0b01L << 58) + (0b01L << 59) + (0b01L << 60) + (0b01L << 61) + (0b01L << 62) + (0b01L << 63);

    private String generateY(long mask) {
        if ((mask & FIRST_ROW_MASK) > 0) {
            return "1";
        } else if ((mask & SECOND_ROW_MASK) != 0b00L) {
            return "2";
        } else if ((mask & THIRD_ROW_MASK) != 0b00L) {
            return "3";
        } else if ((mask & FORTH_ROW_MASK) != 0b00L) {
            return "4";
        } else if ((mask & FIFTH_ROW_MASK) != 0b00L) {
            return "5";
        } else if ((mask & SIXTH_ROW_MASK) != 0b00L) {
            return "6";
        } else if ((mask & SEVENTH_ROW_MASK) != 0b00L) {
            return "7";
        } else if ((mask & EIGHTH_ROW_MASK) != 0b00L) {
            return "8";
        } else {
            logger.info("Mask {}", Long.toBinaryString(EIGHTH_ROW_MASK));
            logger.error("Mask {} does not exist on board in generateY", Long.toBinaryString(mask));
            return "-1";
        }

    }
}
