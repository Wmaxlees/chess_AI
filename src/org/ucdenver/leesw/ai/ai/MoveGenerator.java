package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.board.Board;

import java.util.Collection;

/**
 * Created by william.lees on 9/10/15.
 */
public interface MoveGenerator {

    Collection<Move> generateMoves(Board board, boolean team);
}
