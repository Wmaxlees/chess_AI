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
import java.util.List;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessMoveGenerator implements MoveGenerator {
    private static Logger logger = LogManager.getLogger(ChessMoveGenerator.class);

    @Override
    public List<Move> generateMoves(Board board, boolean team) {
        ArrayList<Move> result = new ArrayList<>();
        long freeSpaces = ~board.flatten();
        long enemyPieces = board.flattenTeam(!team);

        // Get pawn moves
        byte pawnPiece = team ? Piece.BLACK_PAWN : Piece.WHITE_PAWN;
        result.addAll(this.generateAllPawnMoves(board.getPiecesOfType(pawnPiece), team, freeSpaces, enemyPieces));

        // Get king moves
        byte kingPiece = team ? Piece.BLACK_KING : Piece.WHITE_KING;
        result.addAll(this.generateAllKingMoves(board.getPiecesOfType(kingPiece), team, freeSpaces, enemyPieces));

        // Generate rook moves
        byte rookPiece = team ? Piece.BLACK_ROOK : Piece.WHITE_ROOK;
        result.addAll(this.generateAllRookMoves(board.getPiecesOfType(rookPiece), team, board, false));

        // Generate all bishop moves
        byte bishopPiece = team ? Piece.BLACK_BISHOP : Piece.WHITE_BISHOP;
        result.addAll(this.generateAllBishopMoves(board.getPiecesOfType(bishopPiece), team, board, false));

        // Generate queen moves
        byte queenPiece = team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN;
        result.addAll(this.generateAllQueenMoves(board.getPiecesOfType(queenPiece), team, board));

        return result;
    }

    private List<Move> generateAllPawnMoves(long startState, boolean team, long freeSpaces, long enemyPieces) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00L) {
            return results;
        }

        // Generate moves
        if (team == Team.WHITE) {
            // Move pieces forward one square
            long forward = startState << 8;
            forward &= freeSpaces;

            for (long forwardMove : this.splitOutPieces(forward)) {
                Move move = new ChessMove();
                move.setStartLocation(forwardMove >>> 8);
                move.setTargetLocation(forwardMove);
                if ((forwardMove & BitUtilities.getRowMask(8)) != 0b00) {
                    move.setPiece(Piece.WHITE_QUEEN);
                } else {
                    move.setPiece(Piece.WHITE_PAWN);
                }
                results.add(move);
            }

            // Take moves
            for (long aState : this.splitOutPieces(startState)) {
                long take = 0b00;
                take |= aState << 7;
                take |= aState << 9;
                take &= BitUtilities.getRowMask(BitUtilities.getRow(aState) + 1);
                take &= enemyPieces;

                for (long takeMove : this.splitOutPieces(take)) {
                    Move move = new ChessMove();
                    move.setStartLocation(aState);
                    move.setTargetLocation(takeMove);
                    if ((takeMove & BitUtilities.getRowMask(8)) != 0b00) {
                        move.setPiece(Piece.WHITE_QUEEN);
                    } else {
                        move.setPiece(Piece.WHITE_PAWN);
                    }
                    results.add(move);
                }
            }

        } else {
            // Move pieces down one square
            long forward = startState >>> 8;
            forward &= freeSpaces;
            for (long forwardMove : this.splitOutPieces(forward)) {
                Move move = new ChessMove();
                move.setStartLocation(forwardMove << 8);
                move.setTargetLocation(forwardMove);
                if ((forwardMove & BitUtilities.getRowMask(1)) != 0b00) {
                    move.setPiece(Piece.BLACK_QUEEN);
                } else {
                    move.setPiece(Piece.BLACK_PAWN);
                }
                results.add(move);
            }

            // Take moves
            for (long aState : this.splitOutPieces(startState)) {
                long take = 0b00;
                take |= aState >>> 7;
                take |= aState >>> 9;
                take &= BitUtilities.getRowMask(BitUtilities.getRow(aState) - 1);
                take &= enemyPieces;

                for (long takeMove : this.splitOutPieces(take)) {
                    Move move = new ChessMove();
                    move.setStartLocation(aState);
                    move.setTargetLocation(takeMove);
                    if ((takeMove & BitUtilities.getRowMask(1)) != 0b00) {
                        move.setPiece(Piece.BLACK_QUEEN);
                    } else {
                        move.setPiece(Piece.BLACK_PAWN);
                    }
                    results.add(move);
                }
            }
        }

        return results;
    }

    private List<Move> generateAllKingMoves(long startState, boolean team, long freeSpaces, long enemyPieces) {
        ArrayList<Move> results = new ArrayList<>();

        // Check if there are any pieces to generate moves
        if (startState == 0b00) {
            return results;
        }

        // Generate the individual pieces
        List<Long> pieces = this.splitOutPieces(startState);

        // Sanity check (there should be only one King)
        if (pieces.size() > 1) {
            logger.error("More than one King on a team");
        }

        if (pieces.size() < 1) {            // Should never be true since we checked this
            logger.debug("KING MOVE GENERATION: Reached a point in code that should be unreachable.");
            return results;
        }

        // Generate the moves
        byte piece = team ? Piece.BLACK_KING : Piece.WHITE_KING;
        long startLocation = pieces.get(0);

        boolean atWestEnd = (startLocation & BitUtilities.getColumnMask(1)) != 0b00L;
        boolean atEastEnd = (startLocation & BitUtilities.getColumnMask(8)) != 0b00L;

        // Generate possible moves
        long targets = 0b00L;
        targets |= startLocation >>> 8;     // S
        targets |= startLocation <<  8;     // N

        if (!atEastEnd) {
            targets |= startLocation << 9;  // NE
            targets |= startLocation << 1; // E
            targets |= startLocation >>> 7; // SE
        }

        if (!atWestEnd) {
            targets |= startLocation >>> 1;  // W
            targets |= startLocation <<  7; // NW
            targets |= startLocation >>> 9; // SW
        }

        // Weed out impossible moves
        targets &= freeSpaces | enemyPieces;

        for (long position : this.splitOutPieces(targets)) {
            Move move = new ChessMove();
            move.setStartLocation(startLocation);
            move.setPiece(piece);
            move.setTargetLocation(position);
            results.add(move);
        }

        return results;
    }

    private List<Move> generateAllQueenMoves(long startState, boolean team, Board board) {
        ArrayList<Move> results = new ArrayList<>();

        if (startState == 0b00L){
            return results;
        }

        results.addAll(this.generateAllRookMoves(startState, team, board, true));
        results.addAll(this.generateAllBishopMoves(startState, team, board, true));
        return results;
    }

    private List<Move> generateAllRookMoves(long startState, boolean team, Board board, boolean forQueen) {
        ArrayList<Move> results = new ArrayList<>();
        byte piece;
        if (forQueen) {
            piece = (team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN);
        } else {
            piece = (team ? Piece.BLACK_ROOK : Piece.WHITE_ROOK);
        }

        // Check if there are any pieces to generate moves
        if (startState == 0b00L) {
            return results;
        }

        // Generate the individual pieces
        List<Long> pieces = this.splitOutPieces(startState);

        // Loop through pieces and generate all moves for them
        for (long rook : pieces) {
            Move move = null;
            long lastMove;

            // Generate moves north
            lastMove = rook;
            while ((lastMove & BitUtilities.getRowMask(8)) == 0b00L) {
                lastMove = lastMove << 8;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(rook);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves south
            lastMove = rook;
            while ((lastMove & BitUtilities.getRowMask(1)) == 0b00L) {
                lastMove = lastMove >>> 8;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(rook);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves west
            lastMove = rook;
            while ((lastMove & BitUtilities.getColumnMask(1)) == 0b00L) {
                lastMove = lastMove >>> 1;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(rook);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves east
            lastMove = rook;
            while ((lastMove & BitUtilities.getColumnMask(8)) == 0b00L) {
                lastMove = lastMove << 1;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(rook);
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

    private List<Move> generateAllBishopMoves(long startState, boolean team, Board board, boolean forQueen) {
        ArrayList<Move> results = new ArrayList<>();
        byte piece;
        if (forQueen) {
            piece = (team ? Piece.BLACK_QUEEN : Piece.WHITE_QUEEN);
        } else {
            piece = (team ? Piece.BLACK_BISHOP : Piece.WHITE_BISHOP);
        }

        // Check if there are any pieces to generate moves
        if (startState == 0b00L) {
            return results;
        }

        // Generate the individual pieces
        List<Long> pieces = this.splitOutPieces(startState);

        // Loop through pieces and generate all moves for them
        for (long bishop : pieces) {
            Move move;
            long lastMove;

            // Generate moves north-west
            lastMove = bishop;
            while ((lastMove & BitUtilities.getRowMask(8)) == 0b00L && (lastMove & BitUtilities.getColumnMask(1)) == 0b00L) {
                lastMove = lastMove << 7;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(bishop);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves north-east
            lastMove = bishop;
            while ((lastMove & BitUtilities.getRowMask(8)) == 0b00L && (lastMove & BitUtilities.getColumnMask(8)) == 0b00L) {
                lastMove = lastMove << 9;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(bishop);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves sout-east
            lastMove = bishop;
            while ((lastMove & BitUtilities.getRowMask(1)) == 0b00L && (lastMove & BitUtilities.getColumnMask(8)) == 0b00L) {
                lastMove = lastMove >>> 7;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(bishop);
                move.setTargetLocation(lastMove);

                // Check if location has other teams piece
                if (board.doesPieceExist(lastMove, !team)) {
                    results.add(move);
                    break;
                }

                results.add(move);
            }

            // Generate moves south-west
            lastMove = bishop;
            while ((lastMove & BitUtilities.getRowMask(1)) == 0b00L && (lastMove & BitUtilities.getColumnMask(1)) == 0b00L) {
                lastMove = lastMove >>> 9;

                // Check if location has own team's piece
                if (board.doesPieceExist(lastMove, team)) {
                    break;
                }

                move = new ChessMove();
                move.setPiece(piece);
                move.setStartLocation(bishop);
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

    private List<Long> splitOutPieces(long state) {
        ArrayList<Long> pieces = new ArrayList<>();

        long mask = 0b01L;
        long top = BitUtilities.getLocationMask(8, 8);
        while (mask != top) {
            long temp = mask & state;
            if (temp != 0b00L) {
                pieces.add(temp);
            }

            mask = mask << 1;
        }

        long temp = mask & state;
        if (temp != 0b00L) {
            pieces.add(temp);
        }

        return pieces;
    }
}
