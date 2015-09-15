package org.ucdenver.leesw.ai.Board;

import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public class Board {
    // Board width and height
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    // The pieces on the board
    ArrayList<Piece> tiles;

    // Initialize the board without any pieces
    private void initializeBoardArray() {
        this.tiles = new ArrayList<>();
    }

    @Override
    // Generate a string representation of the board
    public String toString() {
        String result = "";

        for (int i = BOARD_HEIGHT; i > 0; --i) {
            for (int j = 1; j <= BOARD_WIDTH; ++j) {
                if (this.getTile(j, i) == null) {
                    if ((i + j) % 2 != 0) {
                        result += "\u25A1" + " ";
                    } else {
                        result += "\u25A0" + " ";
                    }
                } else if (this.getTile(j, i) != null) {
                    result += this.getTile(j, i).getSymbol() + " ";
                }
            }
            result += "\n";
        }

        return result;
    }

    public void addPiece(Piece piece) {
        // Add the piece to the board
        this.tiles.add(piece);
    }

    public Board() {
        this.initializeBoardArray();
    }

    // Copy the state of another board
    public Board(Board other) {
        this.tiles = new ArrayList<>();

        for (Piece tile : other.tiles) {
            Piece piece = null;
            try {
                piece = tile.getClass().newInstance();
            } catch (Exception e) {
                System.out.println("Error: Could not create new instance of piece class");
                System.exit(-1);
            }

            piece.setColor(tile.getColor());
            piece.setX(tile.getX());
            piece.setY(tile.getY());

            this.tiles.add(piece);
        }
    }

    // Remove a piece from the board
    public void removePiece(int x, int y) {
        for (int i = 0; i < this.tiles.size(); ++i) {//Piece tile : this.tiles) {
            Piece tile = this.tiles.get(i);
            if (tile.getX() == x && tile.getY() == y) {
                this.tiles.remove(i);
            }
        }
    }

    // Get a piece from a specific X, Y coordinate (if any exists)
    public Piece getTile(int x, int y) {
        for (Piece tile : this.tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }

        return null;
    }

    public List<Piece> getTiles() {
        return this.tiles;
    }
}
