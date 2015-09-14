package org.ucdenver.leesw.ai.players;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.ai.Move;

/**
 * Created by william.lees on 9/10/15.
 */
public interface Player {

    public Board getNextMove();
}
