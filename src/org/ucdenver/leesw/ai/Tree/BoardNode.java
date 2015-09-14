package org.ucdenver.leesw.ai.Tree;

import org.ucdenver.leesw.ai.Board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class BoardNode {
    private List<BoardNode> subTree;
    private BoardNode parent;
    private int value;
    private Board data;

    public BoardNode(Board data) {
        this.data = data;
    }

    public BoardNode getParent() {
        return this.parent;
    }

    public void setParent(BoardNode parent) {
        this.parent = parent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (parent != null && parent.getValue() < value) {
            parent.setValue(value);
        }
        this.value = value;
    }

    public void addNode(BoardNode node) {
        if (this.subTree == null) {
            this.subTree = new ArrayList<>();
        }

        this.subTree.add(node);
    }

    public List<BoardNode> getChildren() {
        return subTree;
    }

    public Board getData() {
        return data;
    }
}
