package org.ucdenver.leesw.ai.Board;

import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.ArrayList;

/**
 * Created by william.lees on 9/10/15.
 */
public class Tile {
    private Piece piece;
    private ArrayList<TileObserver> observers;

    /**
     * Add an observer to the list to be notified when tile
     * state changes
     *
     * @param observer the observer to be added to the list
     */
    public void addObserver(TileObserver observer) {
        this.observers.add(observer);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}
