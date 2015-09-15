package org.ucdenver.leesw.ai.ai.Collections;

import java.util.Stack;

/**
 * Created by william.lees on 9/14/15.
 */
public class SearchStack extends Stack<SearchItem> {
    public void push(MinimaxNode node, int depth) {
        // Create item
        SearchItem item = new SearchItem();
        item.setNode(node);
        item.setDepth(depth);

        // Add item to stack
        this.push(item);
    }
}
