package org.ucdenver.leesw.ai.ai.impl;

import org.ucdenver.leesw.ai.ai.Heurisitic;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.pieces.Team;

/**
 * Created by william.lees on 9/14/15.
 */
public class SimpleChessHeuristic implements Heurisitic {
    @Override
    public int generateValue(Board data, boolean team) {
        int result = 0;

        result += data.getPopulationOfType(Piece.WHITE_KING) * Piece.getValue(Piece.WHITE_KING);
        result += data.getPopulationOfType(Piece.WHITE_PAWN) * Piece.getValue(Piece.WHITE_PAWN);
        result += data.getPopulationOfType(Piece.WHITE_QUEEN) * Piece.getValue(Piece.WHITE_QUEEN);
        result += data.getPopulationOfType(Piece.BLACK_KING) * Piece.getValue(Piece.BLACK_KING);
        result += data.getPopulationOfType(Piece.BLACK_PAWN) * Piece.getValue(Piece.BLACK_PAWN);
        result += data.getPopulationOfType(Piece.BLACK_QUEEN) * Piece.getValue(Piece.BLACK_QUEEN);

        return (team == Team.WHITE) ? result : -result;
    }
}
