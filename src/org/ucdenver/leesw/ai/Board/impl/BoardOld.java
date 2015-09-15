package org.ucdenver.leesw.ai.board.impl;

import javafx.util.Pair;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.board.Coordinates;
import org.ucdenver.leesw.ai.pieces.Piece;

import java.util.*;

/**
 * Created by william.lees on 9/10/15.
 */
public class BoardOld {
    // Board width and height
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    public static final int ARRAY_SIZE = 6;

    // The pieces on the board
    private Collection<Pair<Coordinates, Byte>> pieces;

    public BoardOld() {
        this.initializeBoardArray();
    }

    // Copy the state of another board
    public BoardOld(BoardOld other) {
        this.initializeBoardArray();

        for (Pair<Coordinates, Byte> entry : other.pieces) {
            Coordinates coordinates = entry.getKey();
            this.addPiece(coordinates.getX(), coordinates.getY(), entry.getValue());
        }
    }

    // Initialize the board without any pieces
    private void initializeBoardArray() {
        this.pieces = new ArrayList<>(ARRAY_SIZE);
    }

    // Generate a string representation of the board
    @Override
    public String toString() {
        String result = "";

        for (int i = BOARD_HEIGHT; i > 0; --i) {
            for (int j = 1; j <= BOARD_WIDTH; ++j) {
                if (this.getPiece(j, i) == null) {
                    if ((i + j) % 2 != 0) {
                        result += "\u25A1" + " ";
                    } else {
                        result += "\u25A0" + " ";
                    }
                } else if (this.getPiece(j, i) != null) {
                    result += Piece.getSymbol(this.getPiece(j, i)) + " ";
                }
            }
            result += "\n";
        }

        return result;
    }

    // Add a piece to the board
    public void addPiece(int x, int y, byte piece) {
        this.removePiece(x, y);
        this.pieces.add(new Pair<>(new Coordinates(x, y), piece));
    }

    // Remove a piece from a specific spot on the board
    public void removePiece(int x, int y) {
        Iterator<Pair<Coordinates, Byte>> iter = this.pieces.iterator();
        while (iter.hasNext()) {
            Coordinates coords = iter.next().getKey();

            if (coords.getX() == x && coords.getY() == y) {
                iter.remove();
            }
        }
    }

    // Get a piece from a specific X, Y coordinate (if any exists)
    public Byte getPiece(int x, int y) {
        Iterator<Pair<Coordinates, Byte>> iter = this.pieces.iterator();
        while (iter.hasNext()) {
            Pair item = iter.next();
            Coordinates coords = (Coordinates)item.getKey();

            if (coords.getX() == x && coords.getY() == y) {
                return (Byte)item.getValue();
            }
        }

        return null;
    }

    public void destroy() {
        this.pieces.clear();
    }
}
