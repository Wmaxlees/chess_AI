package org.ucdenver.leesw.ai.board.impl;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.board.BitBoardLayer;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.board.Coordinates;
import org.ucdenver.leesw.ai.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessBitBoard implements Board {

    private static Logger logger = LogManager.getLogger(ChessBitBoard.class);

    private BitBoardLayer[] layers;

    public ChessBitBoard() {
        layers = new BitBoardLayer[Piece.NO_PIECE];
    }

    // Add a piece to the board
    @Override
    public void addPiece(int x, int y, byte piece) {
        // Sanity check
        if (piece > Piece.NO_PIECE) {
            logger.error("Error: Attempting to add piece of unrecognized type: {}", piece);
            return;
        }

        // Get the proper layer
        BitBoardLayer layer = layers[piece];

        // Initialize layer if needed
        if (layer == null) {
            layer = new ChessBitBoardLayer();
        }

        layer.addPiece(x, y);

        // Reset to array
        layers[piece] = layer;
    }

    // Remove a piece from the board
    @Override
    public void removePiece(int x, int y) {
        for (BitBoardLayer layer : layers) {
            layer.removePiece(x, y);
        }
    }

    // Get a piece from a specific X, Y coordinate (if any exists)
    @Override
    public byte getPieceType(int x, int y) {
        for (int i = 0; i < layers.length; ++i) {
            BitBoardLayer layer = layers[i];
            if (layer != null && layer.isPiece(x, y)) {
                return (byte)i;
            }
        }

        return Byte.MAX_VALUE;
    }

    @Override
    public byte getPieceType(long mask) {
        for (int i = 0; i < layers.length; ++i) {
            BitBoardLayer layer = layers[i];
            if (layer != null && (layer.getBoard() & mask) == mask) {
                return (byte)i;
            }
        }

        return Byte.MAX_VALUE;
    }

    @Override
    public boolean doesPieceExist(int x, int y) {
        BitBoardLayer tempBoard = new ChessBitBoardLayer();
        for (BitBoardLayer layer : layers) {
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layer));
        }

        return tempBoard.isPiece(x, y);
    }

    @Override
    public boolean doesPieceExist(long mask) {
        BitBoardLayer tempBoard = new ChessBitBoardLayer();
        for (BitBoardLayer layer : layers) {
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layer));
        }

        return (tempBoard.getBoard() & mask) == mask;
    }


    @Override
    public boolean doesPieceExist(int x, int y, boolean team) {
        BitBoardLayer tempBoard = new ChessBitBoardLayer();
        for (BitBoardLayer layer : layers) {
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layer));
        }

        return tempBoard.isPiece(x, y);
    }

    @Override
    public boolean doesPieceExist(long mask, boolean team) {
        BitBoardLayer tempBoard = new ChessBitBoardLayer();
        for (BitBoardLayer layer : layers) {
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layer));
        }

        return (tempBoard.getBoard() & mask) == mask;
    }

    @Override
    public long getPiecesOfType(byte pieceType) {
        // Sanity check
        if (pieceType > Piece.NO_PIECE) {
            logger.error("Error: Attempting to get pieces of unrecognized type: {}", pieceType);
            return Long.MAX_VALUE;
        }

        // Get the board or make a blank one if none exists
        BitBoardLayer layer = layers[pieceType];
        if (layer == null) {
            layer = new ChessBitBoardLayer();
        }

        return layer.getBoard();
    }

    public void destroy() {
        for (BitBoardLayer layer : layers) {
            layer = null;
        }
    }
}
