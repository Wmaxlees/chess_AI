package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;
import org.ucdenver.leesw.ai.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/15/15.
 */
public interface MinimaxTree {

    int getValue();
    void setValue(int value);

    List<MinimaxNode> getChildren();
    void addChild(MinimaxNode node);

    Board getData();
}
