package org.ucdenver.leesw.ai.players.impl;

import org.ucdenver.leesw.ai.GameLogic;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.players.Player;

/**
 * Created by william.lees on 9/15/15.
 */
public class PlayerReal implements Player {
    @Override
    public Board getNextMove() {
        // NOT IMPLEMENTED
        System.exit(-1);

        return GameLogic.getGame().getBoard();
    }

    @Override
    public int getNodesSearched() {
        return 0;
    }

//    @Override
//    public void generateSearchTreeView(TreeView<MinimaxTree> treeView) {
//    }
//
//    @Override
//    public void addTreeChangeListener(Method method) {
//
//    }
}
