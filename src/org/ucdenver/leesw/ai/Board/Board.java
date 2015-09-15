package org.ucdenver.leesw.ai.Board;

import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.*;

/**
 * Created by william.lees on 9/10/15.
 */
public class Board {
    // Board width and height
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    // The pieces on the board
    // ArrayList<Piece> tiles;
    HashMap<Coordinates, Piece> pieces;

    public Board() {
        this.initializeBoardArray();
    }

    // Copy the state of another board
    public Board(Board other) {
        this.pieces = new HashMap<>();

        for (Map.Entry<Coordinates, Piece> entry : other.pieces.entrySet()) {
            Piece piece = null;
            Coordinates coordinates = entry.getKey();
            Piece otherPiece = entry.getValue();
            try {
                piece = otherPiece.getClass().newInstance();
            } catch (Exception e) {
                System.out.println("Error: Could not create new instance of piece class");
                System.exit(-1);
            }

            piece.setColor(otherPiece.getColor());

            this.pieces.put(new Coordinates(coordinates.getX(), coordinates.getY()), piece);
        }
    }

    // Initialize the board without any pieces
    private void initializeBoardArray() {
        this.pieces = new HashMap<>();
    }

    @Override
    // Generate a string representation of the board
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
                    result += this.getPiece(j, i).getSymbol() + " ";
                }
            }
            result += "\n";
        }

        return result;
    }

    // Add a piece to the board
    public void addPiece(int x, int y, Piece piece) {
        // Add the piece to the board
        this.pieces.put(new Coordinates(x, y), piece);
    }

    // Remove a piece from the board
    public void removePiece(int x, int y) {
        this.pieces.remove(new Coordinates(x, y));
    }

    // Get a piece from a specific X, Y coordinate (if any exists)
    public Piece getPiece(int x, int y) {
        return this.pieces.get(new Coordinates(x, y));
    }

    public HashMap<Coordinates, Piece> getPieces() {
        return this.pieces;
    }
}
