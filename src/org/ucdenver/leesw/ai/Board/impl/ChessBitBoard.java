package org.ucdenver.leesw.ai.board.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.pieces.Piece;


/**
 * Created by william.lees on 9/15/15.
 */
public class ChessBitBoard implements Board {
    private static Logger logger = LogManager.getLogger(ChessBitBoard.class);
    private String description;
    private long[] layers;

    public ChessBitBoard() {
        layers = new long[Piece.NO_PIECE];

        for (byte i = 0; i < layers.length; ++i) {
            this.layers[i] = ChessBitBoardLayerUtil.getEmptyBoard();
        }
    }

    public ChessBitBoard(Board other) {
        layers = new long[Piece.NO_PIECE];

        if (other != null) {
            for (byte i = 0; i < layers.length; ++i) {
                this.layers[i] = other.getPiecesOfType(i);
            }
        }
    }

    // Add a piece to the board
    @Override
    public void addPiece(int x, int y, byte piece) {
        // Sanity check
        if (piece > this.layers.length) {
            logger.error("Error: Attempting to add piece of unrecognized type: {}", piece);
            return;
        }
        logger.info("Adding piece to the board (x: {}, y: {}, {})", x, y, Piece.getSymbol(piece));

        // Reset to array
        layers[piece] = ChessBitBoardLayerUtil.addPiece(layers[piece], x, y);
    }

    @Override
    public void addPiece(long mask, byte piece) {
        // Sanity check
        if (piece > this.layers.length) {
            logger.error("Error: Attempting to add piece of unrecognized type: {}", piece);
            return;
        }

        layers[piece] = ChessBitBoardLayerUtil.addPiece(layers[piece], mask);
    }

    // Remove a piece from the board
    @Override
    public void removePiece(int x, int y) {
        for (byte i = 0; i < this.layers.length; ++i) {
            layers[i] = ChessBitBoardLayerUtil.removePiece(layers[i], x, y);
        }
    }

    @Override
    public void removePiece(long mask) {
        for (byte i = 0; i < this.layers.length; ++i) {
            layers[i] = ChessBitBoardLayerUtil.removePiece(layers[i], mask);
        }
    }

    // Get a piece from a specific X, Y coordinate (if any exists)
    @Override
    public byte getPieceType(int x, int y) {
        for (byte i = 0; i < this.layers.length; ++i) {
            if (ChessBitBoardLayerUtil.isPiece(layers[i], x, y)) {
                return i;
            }
        }

        return Byte.MAX_VALUE;
    }

    @Override
    public byte getPieceType(long mask) {
        for (byte i = 0; i < this.layers.length; ++i) {
            if (ChessBitBoardLayerUtil.isPiece(layers[i], mask)) {
                return i;
            }
        }

        return Byte.MAX_VALUE;
    }

    @Override
    public boolean doesPieceExist(int x, int y) {
        return ChessBitBoardLayerUtil.isPiece(this.flatten(), x, y);
    }

    @Override
    public boolean doesPieceExist(long mask) {
        return ChessBitBoardLayerUtil.isPiece(this.flatten(), mask);
    }


    @Override
    public boolean doesPieceExist(int x, int y, boolean team) {
        return ChessBitBoardLayerUtil.isPiece(this.flattenTeam(team), x, y);
    }

    @Override
    public boolean doesPieceExist(long mask, boolean team) {
        return ChessBitBoardLayerUtil.isPiece(this.flattenTeam(team), mask);
    }

    @Override
    public long getPiecesOfType(byte pieceType) {
        // Sanity check
        if (pieceType > this.layers.length) {
            logger.error("Error: Attempting to get pieces of unrecognized type: {}", pieceType);
            return Long.MAX_VALUE;
        }

        return layers[pieceType];
    }

    @Override
    public void destroy() {
        this.layers = null;
    }

    @Override
    public byte getPopulationOfType(byte pieceType) {
        // Sanity check
        if (pieceType > this.layers.length) {
            logger.error("Attempting to get population of piece type that doesn't exist: {}", pieceType);
            return 0;
        }

        return ChessBitBoardLayerUtil.getPopulationCount(this.layers[pieceType]);
    }

    @Override
    public void applyMove(Move move) {
        this.description = move.toString();

        this.removePiece(move.getStartLocation());
        this.removePiece(move.getTargetLocation());
        this.addPiece(move.getTargetLocation(), move.getPiece());
    }

    @Override
    public String getMoveDescription() {
        return (description != null) ? description : "N/A";
    }

    @Override
    public long flatten() {
        long result = 0b00L;
        for (long layer : this.layers) {
            result |= layer;
        }

        return result;
    }

    @Override
    public long flattenTeam(boolean team) {
        long result = 0b00L;
        if (team) {
            result |= layers[Piece.BLACK_KING];
            result |= layers[Piece.BLACK_QUEEN];
            result |= layers[Piece.BLACK_PAWN];
            result |= layers[Piece.BLACK_BISHOP];
            result |= layers[Piece.BLACK_ROOK];
            result |= layers[Piece.BLACK_KNIGHT];
        } else {
            result |= layers[Piece.WHITE_KING];
            result |= layers[Piece.WHITE_QUEEN];
            result |= layers[Piece.WHITE_PAWN];
            result |= layers[Piece.WHITE_BISHOP];
            result |= layers[Piece.WHITE_ROOK];
            result |= layers[Piece.WHITE_KNIGHT];
        }

        return result;
    }
}
