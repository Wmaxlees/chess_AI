package org.ucdenver.leesw.ai.players;

import javafx.scene.control.TreeView;
import org.ucdenver.leesw.ai.ai.MinimaxTree;
import org.ucdenver.leesw.ai.board.Board;

import java.lang.reflect.Method;

/**
 * Created by william.lees on 9/10/15.
 */
public interface Player {

    Board getNextMove();

    int getNodesSearched();

    // void generateSearchTreeView(TreeView<MinimaxTree> treeView);
}
