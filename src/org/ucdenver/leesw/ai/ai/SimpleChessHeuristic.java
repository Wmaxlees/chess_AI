package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class SimpleChessHeuristic implements Heurisitic {
    @Override
    public int generateValue(Object data) {
        int result = 0;

        List<Piece> list = ((Board)data).getTiles();
        for (Piece tile : list) {
            result += tile.getValue();
        }

        return result;
    }
}
