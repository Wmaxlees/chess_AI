package org.ucdenver.leesw.ai.Board;

import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public class Board {

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    private Tile[/*x*/][/*y*/] tiles;

    private void initializeBoardArray() {
        this.tiles = new Tile[BOARD_WIDTH+1][BOARD_HEIGHT+1];
        for (int i = BOARD_HEIGHT; i > 0; --i) {
            for (int j = 1; j <= BOARD_WIDTH; ++j) {
                tiles[j][i] = new Tile();
            }
        }
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = BOARD_HEIGHT; i > 0; --i) {
            for (int j = 1; j <= BOARD_WIDTH; ++j) {
                if (this.getTile(j, i) == null) {
                    continue;
                } else if (this.getTile(j, i).getPiece() != null) {
                    result += this.getTile(j, i).getPiece().getSymbol() + " ";
                } else {
                    if ((i + j) % 2 != 0) {
                        result += "\u25A1" + " ";
                    } else {
                        result += "\u25A0" + " ";
                    }
                }
            }
            result += "\n";
        }

        return result;
    }

    public void addPiece(int x, int y, Piece piece) {
        tiles[x][y].setPiece(piece);
    }

    public Board() {
        this.initializeBoardArray();
    }

    public Board(Board other) {
        this.tiles = new Tile[other.tiles.length][];
        for (int i = 0; i < other.tiles.length; ++i) {
            this.tiles[i] = new Tile[other.tiles[i].length];
            for (int j = 0; j < other.tiles[i].length; ++j) {
                Tile tile = other.tiles[i][j];
                this.tiles[i][j] = new Tile();
                if (tile.getPiece() != null) {
                    Piece piece = null;
                    try {
                        Piece otherPiece = tile.getPiece();
                        piece = otherPiece.getClass().newInstance();
                        piece.setColor(otherPiece.getColor());
                    } catch (Exception e) {
                        System.out.println("Piece class does not exist");
                    }

                    this.tiles[i][j].setPiece(piece);
                }
            }
        }
    }

    public void removePiece(int x, int y) {
        this.tiles[x][y].removePiece();
    }

    public Tile getTile(int x, int y) {
        return this.tiles[x][y];
    }
}
