package org.ucdenver.leesw.ai.ai.impl;

import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.ai.UnknownPieceException;
import org.ucdenver.leesw.ai.board.BitBoardLayer;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoardLayer;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.pieces.Team;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessMoveGenerator {
    private static Logger logger = LogManager.getLogger(ChessMoveGenerator.class);

    private static final long TOP_LAYER_MASK    = (0b01 << 56) + (0b01 << 57) + (0b01 << 58) + (0b01 << 59) + (0b01 << 60) + (0b01 << 61) + (0b01 << 62) + (0b01 << 63);
    private static final long BOTTOM_LAYER_MASK = (0b01 <<  0) + (0b01 <<  1) + (0b01 <<  2) + (0b01 <<  3) + (0b01 <<  4) + (0b01 <<  5) + (0b01 <<  6) + (0b01 <<  7);
    private static final long LEFT_LAYER_MASK   = (0b01 <<  0) + (0b01 <<  8) + (0b01 << 16) + (0b01 << 24) + (0b01 << 32) + (0b01 << 40) + (0b01 << 48) + (0b01 << 56);
    private static final long RIGHT_LAYER_MASK  = (0b01 <<  7) + (0b01 << 15) + (0b01 << 23) + (0b01 << 31) + (0b01 << 39) + (0b01 << 47) + (0b01 << 55) + (0b01 << 63);

    public Collection<Move> generateWhiteMoves(Board board) {
        ArrayList<Move> result = new ArrayList<>();

        // Get white pawn moves
        Collection<Move> possibleWhitePawnMoves = this.generateAllPawnMoves(board.getPiecesOfType(Piece.WHITE_PAWN), Team.WHITE);

        // Process Possible white pawn moves
        Iterator<Move> iter = possibleWhitePawnMoves.iterator();
        while (iter.hasNext()) {
            Move move = iter.next();
            if (move.isCaptureOnly()) {
                if (board.doesPieceExist(move.getTargetLocation(), Team.BLACK)) {
                    move.setCapturing(board.getPieceType(move.getTargetLocation()));
                    result.add(move);
                }
            } else if (move.isFreeSpaceOnly()) {
                if (!board.doesPieceExist(move.getTargetLocation())) {
                    result.add(move);
                }
            } else {
                // Pawns should never reach this point
                logger.error("Illegal Pawn Move: {}", move);
            }

            iter.remove();
        }

        // Get white king moves
        Collection<Move> possibleWhiteKingMoves = this.generateAllKingMoves(board.getPiecesOfType(Piece.WHITE_KING), Team.WHITE);

        // Process possible white king moves
        iter = possibleWhiteKingMoves.iterator();
        while (iter.hasNext()) {
            Move move = iter.next();

            // Make sure there is either an enemy piece or an empty square at target
            if (board.doesPieceExist(move.getTargetLocation(), Team.BLACK)) {
                move.setCapturing(board.getPieceType(move.getTargetLocation()));
                result.add(move);
            } else if (!board.doesPieceExist(move.getTargetLocation(), Team.WHITE)) {
                result.add(move);
            }

            iter.remove();
        }

        return result;
    }

    private Collection<Move> generateAllPawnMoves(long startState, boolean team) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00) {
            return results;
        }

        // Generate the individual pieces
        ArrayList<Long> pieces = this.splitOutPieces(startState);

        // Generate moves
        if (team == Team.WHITE) {
            Move move = null;
            for (long piece : pieces) {
                // Move the piece forward one piece
                move = new ChessMove();
                move.setStartLocation(piece);
                move.setTargetLocation(piece << 8);
                move.setPiece(Piece.WHITE_PAWN);
                move.setFreeSpaceOnly();
                results.add(move);

                // Move the piece forward and to the left
                move = new ChessMove();
                move.setStartLocation(piece);
                move.setTargetLocation(piece << 9);
                move.setPiece(Piece.WHITE_PAWN);
                move.setCaptureOnly();
                results.add(move);

                // Move the piece forward and to the right
                move = new ChessMove();
                move.setStartLocation(piece);
                move.setTargetLocation(piece << 7);
                move.setPiece(Piece.WHITE_PAWN);
                move.setCaptureOnly();
                results.add(move);
            }
        } else {
            Move move = null;
            for (long piece : pieces) {
                // Move the piece back one piece
                move = new ChessMove();
                move.setStartLocation(piece);
                move.setTargetLocation(piece >> 8);
                move.setPiece(Piece.WHITE_PAWN);
                move.setFreeSpaceOnly();
                results.add(move);

                // Move the piece back and to the right
                move = new ChessMove();
                move.setStartLocation(piece);
                move.setTargetLocation(piece >> 9);
                move.setPiece(Piece.WHITE_PAWN);
                move.setCaptureOnly();
                results.add(move);

                // Move the piece back and to the left
                move = new ChessMove();
                move.setStartLocation(piece);
                move.setTargetLocation(piece >> 7);
                move.setPiece(Piece.WHITE_PAWN);
                move.setCaptureOnly();
                results.add(move);
            }
        }


        return results;
    }

    private Collection<Move> generateAllKingMoves(long startState, boolean team) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00) {
            return results;
        }

        // Generate the individual pieces
        ArrayList<Long> pieces = this.splitOutPieces(startState);

        // Sanity check (there should be only one King)
        if (pieces.size() > 1) {
            logger.error("More than one King on a team");
        }

        if (pieces.size() < 1) {            // Should never be true since we checked this
            logger.debug("KING MOVE GENERATION: Reached a point in code that should be unreachable.");
            return results;
        }

        // Generate the moves
        Move move = null;
        byte piece = team ? Piece.BLACK_KING : Piece.WHITE_KING;
        long startLocation = pieces.get(1);
        long targetLocation;

        boolean atTop = (startLocation & TOP_LAYER_MASK) != 0b00;
        boolean atBottom = (startLocation & BOTTOM_LAYER_MASK) != 0b00;
        boolean atLeft = (startLocation & LEFT_LAYER_MASK) != 0b00;
        boolean atRight = (startLocation & RIGHT_LAYER_MASK) != 0b00;

        // North one move
        if (!atTop) {
            move = new ChessMove();
            move.setStartLocation(startLocation);
            move.setPiece(piece);
            move.setTargetLocation(startLocation << 8);
            results.add(move);

            if (!atLeft) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation << 7);
                results.add(move);
            }

            if (!atRight) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation << 9);
                results.add(move);
            }
        }

        // Move west
        if (!atLeft) {
            move = new ChessMove();
            move.setStartLocation(startLocation);
            move.setPiece(piece);
            move.setTargetLocation(startLocation >> 1);
            results.add(move);
        }

        // Move east
        if (!atRight) {
            move = new ChessMove();
            move.setStartLocation(startLocation);
            move.setPiece(piece);
            move.setTargetLocation(startLocation << 1);
            results.add(move);
        }

        // Move south
        if (!atBottom) {
            move = new ChessMove();
            move.setStartLocation(startLocation);
            move.setPiece(piece);
            move.setTargetLocation(startLocation >> 8);
            results.add(move);

            if (!atLeft) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation >> 9);
                results.add(move);
            }

            if (!atRight) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation >> 7);
                results.add(move);
            }
        }

        return results;
    }

    private ArrayList<Long> splitOutPieces(long state) {
        ArrayList<Long> pieces = new ArrayList<>();

        // Split out pieces
        for (int i = 0; i < 64; ++i) {
            long temp = state & (1 << i);
            if (temp != 0) {
                pieces.add(temp);
            }
        }

        return pieces;
    }
}
