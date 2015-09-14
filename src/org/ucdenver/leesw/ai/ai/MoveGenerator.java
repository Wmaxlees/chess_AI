package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.Pieces.Piece;

import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public interface MoveGenerator {

    public List<Move> generateMoves(int x, int y, Piece piece) throws UnknownPieceException;
}
