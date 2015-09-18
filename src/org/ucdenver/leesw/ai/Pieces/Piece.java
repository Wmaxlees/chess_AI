package org.ucdenver.leesw.ai.pieces;

/**
 * Created by william.lees on 9/10/15.
 */
public class Piece {
    // Black Pieces
    public static final byte BLACK_KING     = 0;
    public static final byte BLACK_PAWN     = 1;
    public static final byte BLACK_QUEEN    = 2;
    public static final byte BLACK_BISHOP   = 3;
    public static final byte BLACK_ROOK     = 4;
    public static final byte BLACK_KNIGHT   = 5;

    // White Pieces
    public static final byte WHITE_KING     = 6;
    public static final byte WHITE_PAWN     = 7;
    public static final byte WHITE_QUEEN    = 8;
    public static final byte WHITE_BISHOP   = 9;
    public static final byte WHITE_ROOK     = 10;
    public static final byte WHITE_KNIGHT   = 11;

    // Null Piece
    public static final byte NO_PIECE       = 12;

    private static final String KING_SYMBOL_WHITE  = "♔";
    private static final String KING_SYMBOL_BLACK  = "♚";
    private static final int    KING_VALUE         = 5000;

    private static final String PAWN_SYMBOL_WHITE  = "♙";
    private static final String PAWN_SYMBOL_BLACK  = "♟";
    private static final int    PAWN_VALUE         = 1;

    private static final String QUEEN_SYMBOL_WHITE  = "♕";
    private static final String QUEEN_SYMBOL_BLACK  = "♛";
    private static final int    QUEEN_VALUE         = 9;

    private static final String BISHOP_SYMBOL_WHITE = "♗";
    private static final String BISHOP_SYMBOL_BLACK = "♝";
    private static final int    BISHOP_VALUE        = 3;

    private static final String ROOK_SYMBOL_WHITE   = "♖";
    private static final String ROOK_SYMBOL_BLACK   = "♜";
    private static final int    ROOK_VALUE          = 5;

    private static final String KNIGHT_SYMBOL_WHITE = "♘";
    private static final String KNIGHT_SYMBOL_BLACK = "♞";
    private static final int    KNIGHT_VALUE        = 3;


    public static boolean getColor(byte piece) {
        if (piece >= 6) {
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
            case BLACK_PAWN:
                result = PAWN_SYMBOL_BLACK;
                break;
            case BLACK_BISHOP:
                result = BISHOP_SYMBOL_BLACK;
                break;
            case BLACK_ROOK:
                result = ROOK_SYMBOL_BLACK;
                break;
            case BLACK_KNIGHT:
                result = KNIGHT_SYMBOL_BLACK;
                break;
            case BLACK_QUEEN:
                result = QUEEN_SYMBOL_BLACK;
                break;
            case WHITE_KING:
                result = KING_SYMBOL_WHITE;
                break;
            case WHITE_PAWN:
                result = PAWN_SYMBOL_WHITE;
                break;
            case WHITE_BISHOP:
                result = BISHOP_SYMBOL_WHITE;
                break;
            case WHITE_ROOK:
                result = ROOK_SYMBOL_WHITE;
                break;
            case WHITE_KNIGHT:
                result = KNIGHT_SYMBOL_WHITE;
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
            case BLACK_BISHOP:
            case WHITE_BISHOP:
                value = BISHOP_VALUE;
                break;
            case BLACK_ROOK:
            case WHITE_ROOK:
                value = ROOK_VALUE;
                break;
            case BLACK_KNIGHT:
            case WHITE_KNIGHT:
                value = KNIGHT_VALUE;
                break;
            default:
                break;
        }

        return value;
    }
}
