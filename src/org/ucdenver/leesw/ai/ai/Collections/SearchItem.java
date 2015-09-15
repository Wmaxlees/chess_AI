package org.ucdenver.leesw.ai.ai.Collections;

/**
 * Created by william.lees on 9/14/15.
 */
public class SearchItem {
    private int depth;
    private MinimaxNode node;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public MinimaxNode getNode() {
        return node;
    }

    public void setNode(MinimaxNode node) {
        this.node = node;
    }
}
