package org.ucdenver.leesw.ai.Pieces;

/**
 * Created by william.lees on 9/14/15.
 */
public class Queen extends Piece {

    private static final String PIECE_SYMBOL_WHITE  = "\u2655";
    private static final String PIECE_SYMBOL_BLACK  = "\u265B";
    private static final int    PIECE_VALUE         = 200;

    @Override
    public String getSymbol() {
        return (this.color == Color.BLACK) ? PIECE_SYMBOL_BLACK : PIECE_SYMBOL_WHITE;
    }

    @Override
    public int getValue() {
        return PIECE_VALUE;
    }
}
