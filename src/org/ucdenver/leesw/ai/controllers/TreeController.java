package org.ucdenver.leesw.ai.controllers;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.ai.collections.MinimaxTree;
import org.ucdenver.leesw.ai.systems.Event;
import org.ucdenver.leesw.ai.systems.EventListener;

/**
 * Created by william.lees on 9/16/15.
 */
public class TreeController implements EventListener {

    private static Logger logger = LogManager.getLogger(TreeController.class);

    private int depth;
    private int offset;
    private boolean updated;

    private TreeItem<String> treeRoot;

    public void updateTree(TreeView tree) {
        if (treeRoot != null && this.updated) {
            treeRoot.setExpanded(true);
            tree.setRoot(treeRoot);
            this.updated = false;
        }
    }

    public void processEvent(Event event) {
        if (event.getEventType() == Event.EVENT_TYPE_TREE_BUILT) {
            MinimaxTree tree = (MinimaxTree) event.getData();

            this.treeRoot = this.generateSubtree(tree, this.depth);

            this.updated = true;
        }
    }

    private TreeItem<String> generateSubtree(MinimaxTree data, int searchDepth) {
        TreeItem<String> result = new TreeItem<>();
        if (data.chosen()) {
            result.setValue("==> " + data.toString());
        } else {
            result.setValue(data.toString());
        }

        if (searchDepth == 0 ) {//|| data.getChildren().size() == 0) {
            return result;
        }

        // Check for children
        if (searchDepth > 0 && data.getChildren() != null) {
            for (MinimaxTree child : data.getChildren()) {
                result.getChildren().add(this.generateSubtree(child, searchDepth-1));
            }
        }

        return result;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
