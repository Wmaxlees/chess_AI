package org.ucdenver.leesw.ai.ai.impl;

import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.ai.MoveGenerator;
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
public class ChessMoveGenerator implements MoveGenerator {
    private static Logger logger = LogManager.getLogger(ChessMoveGenerator.class);

    private static final long TOP_LAYER_MASK    = (0b01L << 56) + (0b01L << 57) + (0b01L << 58) + (0b01L << 59) + (0b01L << 60) + (0b01L << 61) + (0b01L << 62) + (0b01L << 63);
    private static final long BOTTOM_LAYER_MASK = (0b01L <<  0) + (0b01L <<  1) + (0b01L <<  2) + (0b01L <<  3) + (0b01L <<  4) + (0b01L <<  5) + (0b01L <<  6) + (0b01L <<  7);
    private static final long LEFT_LAYER_MASK   = (0b01L <<  0) + (0b01L <<  8) + (0b01L << 16) + (0b01L << 24) + (0b01L << 32) + (0b01L << 40) + (0b01L << 48) + (0b01L << 56);
    private static final long RIGHT_LAYER_MASK  = (0b01L <<  7) + (0b01L << 15) + (0b01L << 23) + (0b01L << 31) + (0b01L << 39) + (0b01L << 47) + (0b01L << 55) + (0b01L << 63);

    @Override
    public Collection<Move> generateMoves(Board board, boolean team) {
        ArrayList<Move> result = new ArrayList<>();

        // Get pawn moves
        byte pawnPiece = team ? Piece.BLACK_PAWN : Piece.WHITE_PAWN;
        Collection<Move> possiblePawnMoves = this.generateAllPawnMoves(board.getPiecesOfType(pawnPiece), team);

        // Process possible pawn moves
        Iterator<Move> iter = possiblePawnMoves.iterator();
        while (iter.hasNext()) {
            Move move = iter.next();
            if (move.isCaptureOnly()) {
                if (board.doesPieceExist(move.getTargetLocation(), !team)) {
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

        // Get king moves
        byte kingPiece = team ? Piece.BLACK_KING : Piece.WHITE_KING;
        Collection<Move> possibleKingMoves = this.generateAllKingMoves(board.getPiecesOfType(kingPiece), team);

        // Process possible king moves
        iter = possibleKingMoves.iterator();
        while (iter.hasNext()) {
            Move move = iter.next();

            // Make sure there is either an enemy piece or an empty square at target
            if (board.doesPieceExist(move.getTargetLocation(), !team)) {
                move.setCapturing(board.getPieceType(move.getTargetLocation()));
                result.add(move);
            } else if (!board.doesPieceExist(move.getTargetLocation(), team)) {
                result.add(move);
            }

            iter.remove();
        }

        return result;
    }

    private Collection<Move> generateAllPawnMoves(long startState, boolean team) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00L) {
            return results;
        }

        // Generate the individual pieces
        ArrayList<Long> pieces = this.splitOutPieces(startState);

        // Generate moves
        if (team == Team.WHITE) {
            Move move = null;
            for (long piece : pieces) {

                if ((piece & TOP_LAYER_MASK) == 0b00L) {
                    // Move the piece forward one piece
                    move = new ChessMove();
                    move.setStartLocation(piece);
                    move.setTargetLocation(piece << 8);
                    move.setPiece(Piece.WHITE_PAWN);
                    move.setFreeSpaceOnly();

                    // Check if the pawn should be queened
                    if ((move.getTargetLocation() & TOP_LAYER_MASK) != 0) {
                        move.setPiece(Piece.WHITE_QUEEN);
                    }

                    results.add(move);

                    // Move the piece forward and to the left
                    if ((piece & LEFT_LAYER_MASK) == 0b00L) {
                        move = new ChessMove();
                        move.setStartLocation(piece);
                        move.setTargetLocation(piece << 7);
                        move.setPiece(Piece.WHITE_PAWN);
                        move.setCaptureOnly();

                        // Check if the pawn should be queened
                        if ((move.getTargetLocation() & TOP_LAYER_MASK) != 0) {
                            move.setPiece(Piece.WHITE_QUEEN);
                        }

                        results.add(move);
                    }

                    // Move the piece forward and to the right
                    if ((piece & RIGHT_LAYER_MASK) == 0b00L) {
                        move = new ChessMove();
                        move.setStartLocation(piece);
                        move.setTargetLocation(piece << 9);
                        move.setPiece(Piece.WHITE_PAWN);
                        move.setCaptureOnly();

                        // Check if the pawn should be queened
                        if ((move.getTargetLocation() & TOP_LAYER_MASK) != 0) {
                            move.setPiece(Piece.WHITE_QUEEN);
                        }

                        results.add(move);
                    }
                }
            }
        } else {
            Move move = null;
            for (long piece : pieces) {
                if ((piece & BOTTOM_LAYER_MASK) == 0b00L) {
                    // Move the piece back one piece
                    move = new ChessMove();
                    move.setStartLocation(piece);
                    move.setTargetLocation(piece >>> 8);
                    move.setPiece(Piece.BLACK_PAWN);
                    move.setFreeSpaceOnly();
                    results.add(move);

                    // Check if the pawn should be queened
                    if ((move.getTargetLocation() & BOTTOM_LAYER_MASK) != 0) {
                        move.setPiece(Piece.BLACK_QUEEN);
                    }

                    // Move the piece back and to the right
                    if ((piece & RIGHT_LAYER_MASK) == 0b00L) {
                        move = new ChessMove();
                        move.setStartLocation(piece);
                        move.setTargetLocation(piece >>> 7);
                        move.setPiece(Piece.BLACK_PAWN);
                        move.setCaptureOnly();

                        // Check if the pawn should be queened
                        if ((move.getTargetLocation() & BOTTOM_LAYER_MASK) != 0) {
                            move.setPiece(Piece.BLACK_QUEEN);
                        }

                        results.add(move);
                    }

                    // Move the piece back and to the left
                    if ((piece & LEFT_LAYER_MASK) == 0b00L) {
                        move = new ChessMove();
                        move.setStartLocation(piece);
                        move.setTargetLocation(piece >>> 9);
                        move.setPiece(Piece.BLACK_PAWN);
                        move.setCaptureOnly();

                        // Check if the pawn should be queened
                        if ((move.getTargetLocation() & BOTTOM_LAYER_MASK) != 0) {
                            move.setPiece(Piece.BLACK_QUEEN);
                        }

                        results.add(move);
                    }
                }
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
        long startLocation = pieces.get(0);

        boolean atTop = (startLocation & TOP_LAYER_MASK) != 0b00L;
        boolean atBottom = (startLocation & BOTTOM_LAYER_MASK) != 0b00L;
        boolean atLeft = (startLocation & LEFT_LAYER_MASK) != 0b00L;
        boolean atRight = (startLocation & RIGHT_LAYER_MASK) != 0b00L;

        // North one move
        if (!atTop) {
            move = new ChessMove();
            move.setStartLocation(startLocation);
            move.setPiece(piece);
            move.setTargetLocation(startLocation << 8);
            results.add(move);

            // North West
            if (!atLeft) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation << 7);
                results.add(move);
            }

            // North East
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
            move.setTargetLocation(startLocation >>> 1);
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
            move.setTargetLocation(startLocation >>> 8);
            results.add(move);

            if (!atLeft) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation >>> 9);
                results.add(move);
            }

            if (!atRight) {
                move = new ChessMove();
                move.setStartLocation(startLocation);
                move.setPiece(piece);
                move.setTargetLocation(startLocation >>> 7);
                results.add(move);
            }
        }

        return results;
    }

    private Collection<Move> generateAllQueenMoves(long startState, boolean team) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00) {
            return results;
        }

        // Generate the individual pieces
        ArrayList<Long> pieces = this.splitOutPieces(startState);



        return results;
    }

    private ArrayList<Long> splitOutPieces(long state) {
        ArrayList<Long> pieces = new ArrayList<>();

        // Split out pieces
        for (long i = 0L; i < 64L; ++i) {
            long temp = state & (0b01L << i);
            if (temp != 0L) {
                pieces.add(temp);
            }
        }

        return pieces;
    }
}
