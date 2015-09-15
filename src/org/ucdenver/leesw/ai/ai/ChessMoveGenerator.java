package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Pieces.*;

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
                    Move move = new Move(x, y - 1);           // Move south one space
                    move.setStartX(x);
                    move.setStartY(y);
                    if (y-1 == 1) {
                        Queen queen = new Queen();
                        queen.setColor(piece.getColor());
                        move.setPiece(queen);
                    } else {
                        move.setPiece(piece);
                    }
                    moves.add(move);
                }
            } else {
                boolean atTop = y == Board.BOARD_HEIGHT;

                if (!atTop) {
                    Move move = new Move(x, y + 1);           // Move north one space
                    move.setStartX(x);
                    move.setStartY(y);
                    if (y+1 == Board.BOARD_HEIGHT) {
                        Queen queen = new Queen();
                        queen.setColor(piece.getColor());
                        move.setPiece(queen);
                    } else {
                        move.setPiece(piece);
                    }
                    moves.add(move);
                }
            }
        } else if (piece.getClass() == Queen.class) {
            int newX = piece.getX();
            while (++newX <= Board.BOARD_WIDTH) {
                Move move = new Move(newX, piece.getY());
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            newX = piece.getX();
            while (--newX >= 1) {
                Move move = new Move(newX, piece.getY());
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            int newY = piece.getY();
            while(++newY <= Board.BOARD_HEIGHT) {
                Move move = new Move(piece.getX(), newY);
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            newY = piece.getY();
            while(--newY >= 1) {
                Move move = new Move(piece.getX(), newY);
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            newX = piece.getX();
            newY = piece.getY();
            while(++newX <= Board.BOARD_WIDTH && ++newY <= Board.BOARD_HEIGHT) {
                Move move = new Move(newX, newY);
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            newX = piece.getX();
            newY = piece.getY();
            while(++newX <= Board.BOARD_WIDTH && --newY >= 1) {
                Move move = new Move(newX, newY);
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            newX = piece.getX();
            newY = piece.getY();
            while(--newX >= 1 && ++newY <= Board.BOARD_HEIGHT) {
                Move move = new Move(newX, newY);
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }

            newX = piece.getX();
            newY = piece.getY();
            while(--newX >= 1 && --newY >= 1) {
                Move move = new Move(newX, newY);
                move.setStartX(piece.getX());
                move.setStartY(piece.getY());
                move.setPiece(piece);
                moves.add(move);
            }
        } else {
            throw new UnknownPieceException();
        }

        return moves;
    }
}
