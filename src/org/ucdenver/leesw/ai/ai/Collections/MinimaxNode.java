package org.ucdenver.leesw.ai.ai.collections;

import org.ucdenver.leesw.ai.board.impl.BoardOld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.lees on 9/14/15.
 */
public class MinimaxNode {
    private List<MinimaxNode> subTree;
    private int value;
    private BoardOld data;

    public MinimaxNode(BoardOld data) {
        this.data = data;
    }

    public int getValue() {
        return value;
    }

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

    public BoardOld getData() {
        return data;
    }

    public static void addNode(MinimaxNode child, MinimaxNode parent) {
        parent.addChild(child);
    }

    public static void clear(MinimaxNode root) {
        if (root.subTree != null && root.subTree.size() > 0) {
            root.subTree.clear();
        }

    }

    private static void clearSubNode(MinimaxNode node) {
        if (node.subTree != null && node.subTree.size() > 0) {
            for (MinimaxNode subNode : node.subTree) {
                clearSubNode(subNode);
            }
            node.subTree.clear();
        }
        node.data.destroy();
    }
}
