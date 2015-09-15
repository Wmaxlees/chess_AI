package org.ucdenver.leesw.ai.board;

import javafx.util.Pair;
import org.ucdenver.leesw.ai.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by william.lees on 9/15/15.
 */
public interface Board {
    // Add a piece to the board
    void addPiece(int x, int y, byte piece);

    // Remove a piece from a specific spot on the board
    void removePiece(int x, int y);

    // Get a piece type from a specific X, Y coordinate (if any exists)
    byte getPieceType(int x, int y);
    byte getPieceType(long mask);

    // Population questions
    byte getPopulationOfType(byte type);

    // Check if piece exists
    boolean doesPieceExist(int x, int y);
    boolean doesPieceExist(long mask);
    boolean doesPieceExist(int x, int y, boolean team);
    boolean doesPieceExist(long mask, boolean team);

    void destroy();

    long getPiecesOfType(byte pieceType);
}
