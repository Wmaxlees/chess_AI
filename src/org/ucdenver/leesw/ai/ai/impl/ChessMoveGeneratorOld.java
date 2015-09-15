package org.ucdenver.leesw.ai.ai.impl;

import org.ucdenver.leesw.ai.ai.MoveGenerator;
import org.ucdenver.leesw.ai.ai.UnknownPieceException;
import org.ucdenver.leesw.ai.board.impl.BoardOld;
import org.ucdenver.leesw.ai.pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public class ChessMoveGeneratorOld {

    public List<MoveOld> generateMoves(int x, int y, byte piece) throws UnknownPieceException {
        ArrayList<MoveOld> moves = new ArrayList<>();

        if (piece == Piece.BLACK_KING || piece == Piece.WHITE_KING) {

            boolean atTop = y == BoardOld.BOARD_HEIGHT;
            boolean atBottom = y == 1;
            boolean atRight = x == BoardOld.BOARD_WIDTH;
            boolean atLeft = x == 1;

            if (!atTop) {
                MoveOld move = new MoveOld(x, y+1);           // Move north one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            if (!atBottom) {
                MoveOld move = new MoveOld(x, y-1);           // Move south one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            if (!atLeft) {
                MoveOld move = new MoveOld(x-1, y);           // Move west one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);

                if (!atTop) {
                    MoveOld move2 = new MoveOld(x-1, y+1);    // Move north-west one space
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setPiece(piece);
                    moves.add(move2);
                }
                if (!atBottom) {
                    MoveOld move3 = new MoveOld(x-1, y-1);    // Move south-west one space
                    move3.setStartX(x);
                    move3.setStartY(y);
                    move3.setPiece(piece);
                    moves.add(move3);
                }
            }

            if (!atRight) {
                MoveOld move = new MoveOld(x+1, y);           // Move east one space
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);

                if (!atTop) {
                    MoveOld move2 = new MoveOld(x+1, y+1);    // Move north-east one space
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setPiece(piece);
                    moves.add(move2);
                }
                if (!atBottom) {
                    MoveOld move3 = new MoveOld(x+1, y-1);    // Move south-east one space
                    move3.setStartX(x);
                    move3.setStartY(y);
                    move3.setPiece(piece);
                    moves.add(move3);
                }
            }
        } else if (piece == Piece.BLACK_PAWN) {
            boolean atBottom = y == 1;

            if (!atBottom) {
                MoveOld move = new MoveOld(x, y - 1);           // Move south one space
                move.setStartX(x);
                move.setStartY(y);
                move.setFreeSpaceOnly(true);
                if (y-1 == 1) {
                    move.setPiece(Piece.BLACK_QUEEN);
                } else {
                    move.setPiece(piece);
                }
                moves.add(move);

                if (x-1 > 0) {
                    MoveOld move2 = new MoveOld(x - 1, y - 1);
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setCaptureOnly(true);
                    if (y-1 == 1) {
                        move2.setPiece(Piece.BLACK_QUEEN);
                    } else {
                        move2.setPiece(piece);
                    }
                    moves.add(move2);
                }

                if (x+1 <= BoardOld.BOARD_WIDTH) {
                    MoveOld move2 = new MoveOld(x + 1, y - 1);
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setCaptureOnly(true);
                    if (y-1 == 1) {
                        move2.setPiece(Piece.BLACK_QUEEN);
                    } else {
                        move2.setPiece(piece);
                    }
                    moves.add(move2);
                }

            }
        } else if (piece == Piece.WHITE_PAWN){
            boolean atTop = y == BoardOld.BOARD_HEIGHT;

            if (!atTop) {
                MoveOld move = new MoveOld(x, y + 1);           // Move north one space
                move.setStartX(x);
                move.setStartY(y);
                move.setFreeSpaceOnly(true);
                if (y+1 == BoardOld.BOARD_HEIGHT) {
                    move.setPiece(Piece.WHITE_QUEEN);
                } else {
                    move.setPiece(piece);
                }
                moves.add(move);

                if (x-1 > 0) {
                    MoveOld move2 = new MoveOld(x - 1, y + 1);
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setCaptureOnly(true);
                    if (y-1 == 1) {
                        move2.setPiece(Piece.BLACK_QUEEN);
                    } else {
                        move2.setPiece(piece);
                    }
                    moves.add(move2);
                }

                if (x+1 <= BoardOld.BOARD_WIDTH) {
                    MoveOld move2 = new MoveOld(x + 1, y + 1);
                    move2.setStartX(x);
                    move2.setStartY(y);
                    move2.setCaptureOnly(true);
                    if (y-1 == 1) {
                        move2.setPiece(Piece.BLACK_QUEEN);
                    } else {
                        move2.setPiece(piece);
                    }
                    moves.add(move2);
                }
            }
        } else if (piece == Piece.BLACK_QUEEN || piece == Piece.WHITE_QUEEN) {
            int newX = x;
            while (++newX <= BoardOld.BOARD_WIDTH) {
                MoveOld move = new MoveOld(newX, y);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            newX = x;
            while (--newX >= 1) {
                MoveOld move = new MoveOld(newX, y);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            int newY = y;
            while(++newY <= BoardOld.BOARD_HEIGHT) {
                MoveOld move = new MoveOld(x, newY);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            newY = y;
            while(--newY >= 1) {
                MoveOld move = new MoveOld(x, newY);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            newX = x;
            newY = y;
            while(++newX <= BoardOld.BOARD_WIDTH && ++newY <= BoardOld.BOARD_HEIGHT) {
                MoveOld move = new MoveOld(newX, newY);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            newX = x;
            newY = y;
            while(++newX <= BoardOld.BOARD_WIDTH && --newY >= 1) {
                MoveOld move = new MoveOld(newX, newY);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            newX = x;
            newY = y;
            while(--newX >= 1 && ++newY <= BoardOld.BOARD_HEIGHT) {
                MoveOld move = new MoveOld(newX, newY);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }

            newX = x;
            newY = y;
            while(--newX >= 1 && --newY >= 1) {
                MoveOld move = new MoveOld(newX, newY);
                move.setStartX(x);
                move.setStartY(y);
                move.setPiece(piece);
                moves.add(move);
            }
        } else {
            throw new UnknownPieceException();
        }

        return moves;
    }
}
