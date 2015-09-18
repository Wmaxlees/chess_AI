package org.ucdenver.leesw.ai.ai.collections;

import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;
import org.ucdenver.leesw.ai.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/15/15.
 */
public interface MinimaxTree {
    short getValue();
    void setValue(short value);

    List<MinimaxNode> getChildren();
    void addChild(MinimaxNode node);

    Board getData();

    boolean chosen();
    void choose();
}
