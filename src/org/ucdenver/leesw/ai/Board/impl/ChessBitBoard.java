package org.ucdenver.leesw.ai.board.impl;

import javafx.scene.control.TreeView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.board.BitBoardLayer;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.pieces.Piece;


/**
 * Created by william.lees on 9/15/15.
 */
public class ChessBitBoard implements Board {

    private static Logger logger = LogManager.getLogger(ChessBitBoard.class);

    private String description;

    private BitBoardLayer[] layers;

    public ChessBitBoard() {
        layers = new BitBoardLayer[Piece.NO_PIECE];
    }

    public ChessBitBoard(Board other) {
        layers = new BitBoardLayer[Piece.NO_PIECE];

        if (other != null) {
            for (byte i = 0; i < layers.length; ++i) {
                this.layers[i] = new ChessBitBoardLayer();
                this.layers[i].setBoard(other.getPiecesOfType(i));
            }
        }
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

        logger.info("Adding piece to the board (x: {}, y: {}, {})", x, y, Piece.getSymbol(piece));
        layer.addPiece(x, y);

        // Reset to array
        layers[piece] = layer;
    }

    @Override
    public void addPiece(long mask, byte piece) {
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

        layer.addPiece(mask);

        layers[piece] = layer;
    }

    // Remove a piece from the board
    @Override
    public void removePiece(int x, int y) {
        for (BitBoardLayer layer : layers) {
            layer.removePiece(x, y);
        }
    }

    @Override
    public void removePiece(long mask) {
        for (BitBoardLayer layer : layers) {
            layer.removePiece(mask);
        }
    }

    // Get a piece from a specific X, Y coordinate (if any exists)
    @Override
    public byte getPieceType(int x, int y) {
        for (byte i = 0; i < layers.length; ++i) {
            BitBoardLayer layer = layers[i];
            if (layer != null && layer.isPiece(x, y)) {
                return i;
            }
        }

        return Byte.MAX_VALUE;
    }

    @Override
    public byte getPieceType(long mask) {
        for (byte i = 0; i < layers.length; ++i) {
            BitBoardLayer layer = layers[i];
            if (layer != null && (layer.getBoard() & mask) == mask) {
                return i;
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
        if (team) {     // Black
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.BLACK_KING]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.BLACK_PAWN]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.BLACK_QUEEN]));
        } else {        // White
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.WHITE_KING]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.WHITE_PAWN]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.WHITE_QUEEN]));
        }

        return tempBoard.isPiece(x, y);
    }

    @Override
    public boolean doesPieceExist(long mask, boolean team) {
        BitBoardLayer tempBoard = new ChessBitBoardLayer();
        if (team) {     // Black
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.BLACK_KING]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.BLACK_PAWN]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.BLACK_QUEEN]));
        } else {        // White
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.WHITE_KING]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.WHITE_PAWN]));
            tempBoard.setBoard(BitBoardLayer.or(tempBoard, layers[Piece.WHITE_QUEEN]));
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

    @Override
    public void destroy() {
        for (BitBoardLayer layer : layers) {
            layer = null;
        }
    }

    @Override
    public byte getPopulationOfType(byte pieceType) {
        // Sanity check
        if (pieceType > Piece.NO_PIECE) {
            logger.error("Attempting to get population of piece type that doesn't exist: {}", pieceType);
            return 0;
        }

        return this.layers[pieceType].getPopulationCount();
    }

    @Override
    public void applyMove(Move move) {
        this.description = move.toString();

        this.removePiece(move.getStartLocation());
        this.addPiece(move.getTargetLocation(), move.getPiece());

        if (move.isCapturing()) {
            this.layers[move.capturePiece()].removePiece(move.getTargetLocation());
        }
    }

    @Override
    public String getMoveDescription() {
        return (description != null) ? description : "N/A";
    }

    @Override
    public long flatten() {
        long result = 0b00L;
        for (BitBoardLayer layer : this.layers) {
            result |= layer.getBoard();
        }

        return result;
    }

    @Override
    public long flattenTeam(boolean team) {
        long result = 0b00L;
        if (team) {
            result |= layers[Piece.BLACK_KING].getBoard();
            result |= layers[Piece.BLACK_QUEEN].getBoard();
            result |= layers[Piece.BLACK_PAWN].getBoard();
        } else {
            result |= layers[Piece.WHITE_KING].getBoard();
            result |= layers[Piece.WHITE_QUEEN].getBoard();
            result |= layers[Piece.WHITE_PAWN].getBoard();
        }

        return result;
    }
}
