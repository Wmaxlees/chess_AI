package org.ucdenver.leesw.ai.ai.impl;

import org.ucdenver.leesw.ai.BitUtilities;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.ai.MoveGenerator;
import org.ucdenver.leesw.ai.board.Board;
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

    @Override
    public Collection<Move> generateMoves(Board board, boolean team) {
        ArrayList<Move> result = new ArrayList<>();
        long freeSpaces = ~board.flatten();
        long enemyPieces = board.flattenTeam(!team);

        // Get pawn moves
        byte pawnPiece = team ? Piece.BLACK_PAWN : Piece.WHITE_PAWN;
       result.addAll(this.generateAllPawnMoves(board.getPiecesOfType(pawnPiece), team, freeSpaces, enemyPieces));

        // Process possible pawn moves
//        Iterator<Move> iter = possiblePawnMoves.iterator();
//        while (iter.hasNext()) {
//            Move move = iter.next();
//            if (move.isCaptureOnly()) {
//                if (board.doesPieceExist(move.getTargetLocation(), !team)) {
//                    move.setCapturing(board.getPieceType(move.getTargetLocation()));
//                    result.add(move);
//                }
//            } else if (move.isFreeSpaceOnly()) {
//                if (!board.doesPieceExist(move.getTargetLocation())) {
//                    result.add(move);
//                }
//            } else {
//                // Pawns should never reach this point
//                logger.error("Illegal Pawn Move: {}", move);
//            }
//
//            iter.remove();
//        }

        // Get king moves
//        byte kingPiece = team ? Piece.BLACK_KING : Piece.WHITE_KING;
//        Collection<Move> possibleKingMoves = this.generateAllKingMoves(board.getPiecesOfType(kingPiece), team);
//
//        // Process possible king moves
//        Iterator<Move> iter = possibleKingMoves.iterator();
//        while (iter.hasNext()) {
//            Move move = iter.next();
//
//            // Make sure there is either an enemy piece or an empty square at target
//            if (board.doesPieceExist(move.getTargetLocation(), !team)) {
//                move.setCapturing(board.getPieceType(move.getTargetLocation()));
//                result.add(move);
//            } else if (!board.doesPieceExist(move.getTargetLocation(), team)) {
//                result.add(move);
//            }
//
//            iter.remove();
//        }
//
//        // Generate queen moves
//        result.addAll(this.generateAllQueenMoves(board.getPiecesOfType(
//                        team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN),
//                        team,
//                        board));

        return result;
    }

    private Collection<Move> generateAllPawnMoves(long startState, boolean team, long freeSpaces, long enemyPieces) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00L) {
            return results;
        }

        // Generate the individual pieces
        ArrayList<Long> pieces = this.splitOutPieces(startState);

        // Generate moves
        if (team == Team.WHITE) {
            // Move pieces forward one square
            long forward = startState << 8;
            forward &= freeSpaces;
            for (long forwardMove : this.splitOutPieces(forward)) {
                Move move = new ChessMove();
                move.setStartLocation(forwardMove >>> 8);
                move.setTargetLocation(forwardMove);
                move.setPiece(Piece.WHITE_PAWN);
//                if ((forwardMove & BitUtilities.getRowMask(8)) != 0b00) {
//                    move.setPiece(Piece.WHITE_QUEEN);
//                } else {
//                    move.setPiece(Piece.WHITE_PAWN);
//                }
                results.add(move);
            }

            // Generate take-left moves
            long take = startState & ~BitUtilities.getColumnMask(1);
            take = take << 7;
            take &= enemyPieces;
            for (long takeMove : this.splitOutPieces(take)) {
                Move move = new ChessMove();
                move.setStartLocation(takeMove >>> 7);
                move.setTargetLocation(takeMove);
                move.setPiece(Piece.WHITE_PAWN);
//                if ((takeMove & BitUtilities.getRowMask(8)) != 0b00) {
//                    move.setPiece(Piece.WHITE_QUEEN);
//                } else {
//                    move.setPiece(Piece.WHITE_PAWN);
//                }
                results.add(move);
            }

            // Generate take-right moves
            take = startState & ~BitUtilities.getColumnMask(8);
            take = take << 9;
            take &= enemyPieces;
            for (long takeMove : this.splitOutPieces(take)) {
                Move move = new ChessMove();
                move.setStartLocation(takeMove >>> 9);
                move.setTargetLocation(takeMove);
                move.setPiece(Piece.WHITE_PAWN);
//                if ((takeMove & BitUtilities.getRowMask(8)) != 0b00) {
//                    move.setPiece(Piece.WHITE_QUEEN);
//                } else {
//                    move.setPiece(Piece.WHITE_PAWN);
//                }
                results.add(move);
            }

        } else {
            // Move pieces down one square
            long forward = startState >>> 8;
            forward &= freeSpaces;
            for (long forwardMove : this.splitOutPieces(forward)) {
                Move move = new ChessMove();
                move.setStartLocation(forwardMove << 8);
                move.setTargetLocation(forwardMove);
                move.setPiece(Piece.BLACK_PAWN);
//                if ((forwardMove & BitUtilities.getRowMask(1)) != 0b00) {
//                    move.setPiece(Piece.BLACK_QUEEN);
//                } else {
//                    move.setPiece(Piece.BLACK_PAWN);
//                }
                results.add(move);
            }

            // Generate take-left moves
            long take = startState & ~BitUtilities.getColumnMask(1);
            take = take >>> 9;
            take &= enemyPieces;
            for (long takeMove : this.splitOutPieces(take)) {
                Move move = new ChessMove();
                move.setStartLocation(takeMove << 9);
                move.setTargetLocation(takeMove);
                move.setPiece(Piece.BLACK_PAWN);
//                if ((takeMove & BitUtilities.getRowMask(1)) != 0b00) {
//                    move.setPiece(Piece.BLACK_QUEEN);
//                } else {
//                    move.setPiece(Piece.BLACK_PAWN);
//                }
                results.add(move);
            }

            // Generate take-right moves
            take = startState & ~BitUtilities.getColumnMask(8);
            take = take >>> 7;
            take &= enemyPieces;
            for (long takeMove : this.splitOutPieces(take)) {
                Move move = new ChessMove();
                move.setStartLocation(takeMove << 7);
                move.setTargetLocation(takeMove);
                move.setPiece(Piece.BLACK_PAWN);
//                if ((takeMove & BitUtilities.getRowMask(1)) != 0b00) {
//                    move.setPiece(Piece.BLACK_QUEEN);
//                } else {
//                    move.setPiece(Piece.BLACK_PAWN);
//                }
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
        long startLocation = pieces.get(0);

        boolean atTop = (startLocation & BitUtilities.getRowMask(8)) != 0b00L;
        boolean atBottom = (startLocation & BitUtilities.getRowMask(1)) != 0b00L;
        boolean atLeft = (startLocation & BitUtilities.getColumnMask(1)) != 0b00L;
        boolean atRight = (startLocation & BitUtilities.getColumnMask(8)) != 0b00L;

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

    private Collection<Move> generateAllQueenMoves(long startState, boolean team, Board board) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00) {
            return results;
        }

        // Generate the individual pieces
        ArrayList<Long> pieces = this.splitOutPieces(startState);

        // Loop through pieces and generate all moves for them
        Move move = null;
        for (long queen : pieces) {
            long lastMove;

            // Generate moves north
            lastMove = queen;
            while ((lastMove & BitUtilities.getRowMask(8)) == 0b00) {
                lastMove = lastMove << 8;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece((team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN));
                move.setStartLocation(queen);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves south
            lastMove = queen;
            while ((lastMove & BitUtilities.getRowMask(1)) == 0b00) {
                lastMove = lastMove >>> 8;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece((team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN));
                move.setStartLocation(queen);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves west
            lastMove = queen;
            while ((lastMove & BitUtilities.getColumnMask(1)) == 0b00) {
                lastMove = lastMove >>> 1;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece((team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN));
                move.setStartLocation(queen);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves east
            lastMove = queen;
            while ((lastMove & BitUtilities.getColumnMask(8)) == 0b00) {
                lastMove = lastMove << 1;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece((team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN));
                move.setStartLocation(queen);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }
        }

        return results;
    }

    private static long TOP_RIGHT_CORNER = 0b01L << 63;
    private ArrayList<Long> splitOutPieces(long state) {
        ArrayList<Long> pieces = new ArrayList<>();

        long mask = 0b01L;
        while (mask != TOP_RIGHT_CORNER) {
            long temp = mask & state;
            if (temp != 0b00L) {
                pieces.add(temp);
            }

            mask = mask << 1;
        }

        return pieces;
    }
}
