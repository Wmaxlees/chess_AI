package org.ucdenver.leesw.ai.ai;

import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;

import java.util.List;

/**
 * Created by william.lees on 9/15/15.
 */
public class MinimaxTreeControl {
    public static void addNode(MinimaxNode child, MinimaxTree parent) {
        parent.addChild(child);
    }

    public static void clear(MinimaxTree root) {
        List<MinimaxNode> children = root.getChildren();
        if (children != null && children.size() > 0) {
            children.clear();
        }

    }
}
