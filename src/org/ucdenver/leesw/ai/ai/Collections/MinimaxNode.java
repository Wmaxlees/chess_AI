package org.ucdenver.leesw.ai.ai.collections;

import org.ucdenver.leesw.ai.ai.MinimaxTree;
import org.ucdenver.leesw.ai.ai.Move;
import org.ucdenver.leesw.ai.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class MinimaxNode implements MinimaxTree {
    private List<MinimaxNode> subTree;
    private int value;
    private Board data;

    public MinimaxNode(Board data) {
        this.data = data;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void addChild(MinimaxNode node) {
        if (this.subTree == null) {
            this.subTree = new ArrayList<>();
        }

        this.subTree.add(node);
    }

    @Override
    public List<MinimaxNode> getChildren() {
        return subTree;
    }

    @Override
    public Board getData() {
        return data;
    }

    @Override
    public String toString() {
        return this.data.getMoveDescription() + " = " + this.value;
    }
}
