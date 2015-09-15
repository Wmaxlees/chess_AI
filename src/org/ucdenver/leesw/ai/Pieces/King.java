package org.ucdenver.leesw.ai.Pieces;

/**
 * Created by william.lees on 9/10/15.
 */
public class King extends Piece {

    private static final String PIECE_SYMBOL_WHITE  = "\u2654";
    private static final String PIECE_SYMBOL_BLACK  = "\u265A";
    private static final int    PIECE_VALUE         = 5000;

    @Override
    public String getSymbol() {
        return (this.color == Color.BLACK) ? PIECE_SYMBOL_BLACK : PIECE_SYMBOL_WHITE;
    }

    @Override
    public int getValue() {
        return PIECE_VALUE;
    }
}
