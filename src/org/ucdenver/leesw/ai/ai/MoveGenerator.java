package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.board.Board;

import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public interface MoveGenerator {

    List<Move> generateMoves(Board board, boolean team);
}
