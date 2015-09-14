package org.ucdenver.leesw.ai.Tree;

import org.ucdenver.leesw.ai.Board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class BoardNode {
    private List<BoardNode> subTree;
    private Board data;

    public BoardNode(Board data) {
        this.data = data;
    }

    public void addNode(BoardNode node) {
        if (this.subTree == null) {
            this.subTree = new ArrayList<>();
        }

        this.subTree.add(node);
    }

    public Board getData() {
        return data;
    }
}
