package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Pieces.Color;
import org.ucdenver.leesw.ai.Pieces.King;
import org.ucdenver.leesw.ai.Pieces.Pawn;
import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public class ChessMoveGenerator implements MoveGenerator {

    @Override
    public List<Move> generateMoves(int x, int y, Piece piece) throws UnknownPieceException{
        ArrayList<Move> moves = new ArrayList<>();

        if (piece.getClass() == King.class) {
            boolean atTop = y == Board.BOARD_HEIGHT;
            boolean atBottom = y == 1;
            boolean atRight = x == Board.BOARD_WIDTH;
            boolean atLeft = x == 1;

            if (!atTop) {
                Move move = new Move(x, y+1);           // Move north one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            if (!atBottom) {
                Move move = new Move(x, y-1);           // Move south one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            if (!atLeft) {
                Move move = new Move(x-1, y);           // Move west one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);

                if (!atTop) {
                    Move move2 = new Move(x-1, y+1);    // Move north-west one space
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setPiece(piece);
                    moves.add(move2);
                }
                if (!atBottom) {
                    Move move3 = new Move(x-1, y-1);    // Move south-west one space
                    move3.setStartX(x);
                    move3.setStartY(y);
                    move3.setPiece(piece);
                    moves.add(move3);
                }
            }

            if (!atRight) {
                Move move = new Move(x+1, y);           // Move east one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);

                if (!atTop) {
                    Move move2 = new Move(x+1, y+1);    // Move north-east one space
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setPiece(piece);
                    moves.add(move2);
                }
                if (!atBottom) {
                    Move move3 = new Move(x+1, y-1);    // Move south-east one space
                    move3.setStartX(x);
                    move3.setStartY(y);
                    move3.setPiece(piece);
                    moves.add(move3);
                }
            }
        } else if (piece.getClass() == Pawn.class) {
            if (piece.getColor() == Color.BLACK) {
                boolean atBottom = y == 1;

                if (!atBottom) {
                    Move move = new Move(x, y-1);           // Move south one space
                    move.setStartX(x);
                    move.setStartY(y);
                    move.setPiece(piece);
                    moves.add(move);
                }
            } else {
                boolean atTop = y == Board.BOARD_HEIGHT;

                if (!atTop) {
                    Move move = new Move(x, y+1);           // Move north one space
                    move.setStartX(x);
                    move.setStartY(y);
                    move.setPiece(piece);
                    moves.add(move);
                }
            }
        } else {
            throw new UnknownPieceException();
        }

        return moves;
    }
}
