package org.ucdenver.leesw.ai.players;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Board.Tile;
import org.ucdenver.leesw.ai.Game;
import org.ucdenver.leesw.ai.Pieces.Color;
import org.ucdenver.leesw.ai.Pieces.Piece;
import org.ucdenver.leesw.ai.ai.ChessMoveGenerator;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.ai.MoveGenerator;
import org.ucdenver.leesw.ai.ai.UnknownPieceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public class PlayerAI implements Player {
    private Color team;
    private MoveGenerator moveGenerator;

    public PlayerAI(Color team) {
        this.team = team;
        this.moveGenerator = new ChessMoveGenerator();
    }

    @Override
    public Board getNextMove() {
        List<Board> possibilities = this.generateSubTree(Game.getGame().getBoard());
        System.out.println("Number of possible moves: " + possibilities.size());

//        for (Board possibility : possibilities) {
//            System.out.println(possibility);
//            System.out.println("----------------------------");
//        }
        return possibilities.get((int)(Math.random()*possibilities.size()));
    }

    private List<Board> generateSubTree(Board state) {
        ArrayList<Board> subStates = new ArrayList<>();

        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i < Board.BOARD_HEIGHT; ++i) {
            for (int j = 1; j < Board.BOARD_WIDTH; ++j) {
                Piece piece = state.getTile(j, i).getPiece();
                if (piece != null && piece.getColor() == this.team) {
                    try {
                        moves.addAll(moveGenerator.generateMoves(j, i, piece));
                    } catch (UnknownPieceException e) {
                        System.out.println("Unknown Piece for " + moveGenerator.getClass());
                        System.exit(1);
                    }
                }
            }
        }


        if (moves != null) {
            for (Move move : moves) {
                System.out.println(move.toString());

                Board board = new Board(state);
                board.removePiece(move.getStartX(), move.getStartY());
                board.addPiece(move.getTargetX(), move.getTargetY(), move.getPiece());

                System.out.print(board);
                System.out.println("----------------------------");

                subStates.add(board);
            }
        }

        return subStates;
    }


}
