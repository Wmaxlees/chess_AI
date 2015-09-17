package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.board.Board;

/**
 * Created by william.lees on 9/14/15.
 */
public interface Heurisitic {
    int generateValue(Board data, boolean team);
}
