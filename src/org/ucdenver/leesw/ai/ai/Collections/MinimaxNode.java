package org.ucdenver.leesw.ai.ai.collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class MinimaxNode implements MinimaxTree {
    private static Logger logger = LogManager.getLogger(MinimaxNode.class);

    private List<MinimaxNode> subTree;
    private short value;
    private Board data;
    private boolean chosen;

    public MinimaxNode(Board data) {
        this.data = data;
    }

    @Override
    public short getValue() {
        return value;
    }

    @Override
    public void setValue(short value) {
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

    @Override
    public boolean chosen() {
        return this.chosen;
    }

    @Override
    public void choose() {
        this.chosen = true;
    }
}
