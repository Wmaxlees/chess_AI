package org.ucdenver.leesw.ai.pieces;

/**
 * Created by william.lees on 9/10/15.
 */
public class Piece {
    // Black Pieces
    public static final byte BLACK_KING = 0;
    public static final byte BLACK_PAWN = 1;
    public static final byte BLACK_QUEEN = 2;

    // White Pieces
    public static final byte WHITE_KING = 3;
    public static final byte WHITE_PAWN = 4;
    public static final byte WHITE_QUEEN = 5;

    // Null Piece
    public static final byte NO_PIECE = 6;

    private static final String KING_SYMBOL_WHITE  = "\u2654".intern();
    private static final String KING_SYMBOL_BLACK  = "\u265A".intern();
    private static final int    KING_VALUE         = 5000;

    private static final String PAWN_SYMBOL_WHITE  = "\u2659".intern();
    private static final String PAWN_SYMBOL_BLACK  = "\u265F".intern();
    private static final int    PAWN_VALUE         = 1;

    private static final String QUEEN_SYMBOL_WHITE  = "\u2655".intern();
    private static final String QUEEN_SYMBOL_BLACK  = "\u265B".intern();
    private static final int    QUEEN_VALUE         = 9;


    public static boolean getColor(byte piece) {
        if (piece >= 3) {
            return Team.WHITE;
        } else {
            return Team.BLACK;
        }
    }

    public static String getSymbol(byte piece) {
        String result = null;

        switch (piece) {
            case BLACK_KING:
                result = KING_SYMBOL_BLACK;
                break;
            case WHITE_KING:
                result = KING_SYMBOL_WHITE;
                break;
            case BLACK_PAWN:
                result = PAWN_SYMBOL_BLACK;
                break;
            case WHITE_PAWN:
                result = PAWN_SYMBOL_WHITE;
                break;
            case BLACK_QUEEN:
                result = QUEEN_SYMBOL_BLACK;
                break;
            case WHITE_QUEEN:
                result = QUEEN_SYMBOL_WHITE;
                break;
            default:
                break;
        }

        return result;
    }

    public static int getValue(byte piece) {
        int value = 0;

        switch (piece) {
            case BLACK_KING:
            case WHITE_KING:
                value = KING_VALUE;
                break;
            case BLACK_PAWN:
            case WHITE_PAWN:
                value = PAWN_VALUE;
                break;
            case BLACK_QUEEN:
            case WHITE_QUEEN:
                value = QUEEN_VALUE;
                break;
            default:
                break;
        }

        return value;
    }
}
