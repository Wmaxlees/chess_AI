package org.ucdenver.leesw.ai.ai.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.pieces.Team;

/**
 * Created by william.lees on 9/14/15.
 */
public class SimpleChessHeuristic {
    private static Logger logger = LogManager.getLogger(SimpleChessHeuristic.class);

    public static short generateValue(Board data, boolean team) {
        short result = 0;

        result += data.getPopulationOfType(Piece.WHITE_KING) * Piece.getValue(Piece.WHITE_KING);
        result += data.getPopulationOfType(Piece.WHITE_PAWN) * Piece.getValue(Piece.WHITE_PAWN);
        result += data.getPopulationOfType(Piece.WHITE_QUEEN) * Piece.getValue(Piece.WHITE_QUEEN);
        result += data.getPopulationOfType(Piece.WHITE_ROOK) * Piece.getValue(Piece.WHITE_ROOK);
        result += data.getPopulationOfType(Piece.WHITE_BISHOP) * Piece.getValue(Piece.WHITE_BISHOP);
        result += data.getPopulationOfType(Piece.WHITE_KNIGHT) * Piece.getValue(Piece.WHITE_KNIGHT);

        result -= data.getPopulationOfType(Piece.BLACK_KING) * Piece.getValue(Piece.BLACK_KING);
        result -= data.getPopulationOfType(Piece.BLACK_PAWN) * Piece.getValue(Piece.BLACK_PAWN);
        result -= data.getPopulationOfType(Piece.BLACK_QUEEN) * Piece.getValue(Piece.BLACK_QUEEN);
        result -= data.getPopulationOfType(Piece.BLACK_ROOK) * Piece.getValue(Piece.BLACK_ROOK);
        result -= data.getPopulationOfType(Piece.BLACK_BISHOP) * Piece.getValue(Piece.BLACK_BISHOP);
        result -= data.getPopulationOfType(Piece.BLACK_KNIGHT) * Piece.getValue(Piece.BLACK_KNIGHT);

        return team ? (short)-result : result;
    }
}
