package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Pieces.Color;
import org.ucdenver.leesw.ai.Pieces.Piece;
import org.ucdenver.leesw.ai.ai.Collections.MinimaxNode;

import java.util.Collection;

/**
 * Created by william.lees on 9/14/15.
 */
public class SimpleChessHeuristic implements Heurisitic {
    @Override
    public int generateValue(Object data, Object team) {
        int result = 0;

        Color color = (Color)team;

        Collection<Piece> list = ((MinimaxNode)data).getData().getPieces().values();
        for (Piece piece : list) {
            result += (piece.getColor() == color) ? piece.getValue() : -piece.getValue();
        }

        return result;
    }
}
