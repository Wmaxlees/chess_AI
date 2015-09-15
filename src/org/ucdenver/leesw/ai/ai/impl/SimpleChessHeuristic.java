package org.ucdenver.leesw.ai.ai.impl;

import javafx.util.Pair;
import org.ucdenver.leesw.ai.ai.Heurisitic;
import org.ucdenver.leesw.ai.board.Coordinates;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;

/**
 * Created by william.lees on 9/14/15.
 */
public class SimpleChessHeuristic implements Heurisitic {
    @Override
    public int generateValue(Object data, Object team) {
        int result = 0;

        boolean color = (boolean)team;

        for (Pair<Coordinates, Byte> piece : ((MinimaxNode)data).getData().getPieces()) {
            result += (Piece.getColor(piece.getValue()) == color) ? Piece.getValue(piece.getValue()) : -Piece.getValue(piece.getValue());
        }

        return result;
    }
}
