package org.ucdenver.leesw.ai.players;

import org.ucdenver.leesw.ai.board.impl.BoardOld;

/**
 * Created by william.lees on 9/10/15.
 */
public interface Player {

    public BoardOld getNextMove();
}
