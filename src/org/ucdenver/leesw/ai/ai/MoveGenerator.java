package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.ai.impl.MoveOld;

import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public interface MoveGenerator {

    public List<MoveOld> generateMoves(int x, int y, byte piece) throws UnknownPieceException;
}
