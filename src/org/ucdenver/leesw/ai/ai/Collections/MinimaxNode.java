package org.ucdenver.leesw.ai.ai.Collections;

import org.ucdenver.leesw.ai.Board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class MinimaxNode {
    private List<MinimaxNode> subTree;
    private MinimaxNode parent;
    private int value;
    private Board data;

    public MinimaxNode(Board data) {
        this.data = data;
    }

    public MinimaxNode getParent() {
        return this.parent;
    }

    public void setParent(MinimaxNode parent) {
        this.parent = parent;
    }

    public int getValue() {
        return value;
    }

    // Set this node's value
    public void setValue(int value) {
        this.value = value;
    }

    public void addChild(MinimaxNode node) {
        if (this.subTree == null) {
            this.subTree = new ArrayList<>();
        }

        this.subTree.add(node);
    }

    public List<MinimaxNode> getChildren() {
        return subTree;
    }

    public Board getData() {
        return data;
    }

    public static void addNode(MinimaxNode child, MinimaxNode parent) {
        parent.addChild(child);
        child.setParent(parent);
    }
}
