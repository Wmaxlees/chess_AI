package org.ucdenver.leesw.ai.players.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.ucdenver.leesw.ai.BitUtilities;
import org.ucdenver.leesw.ai.ai.*;
import org.ucdenver.leesw.ai.ai.collections.MinimaxTree;
import org.ucdenver.leesw.ai.ai.collections.MinimaxTreeControl;
import org.ucdenver.leesw.ai.ai.impl.ChessMoveGenerator;
import org.ucdenver.leesw.ai.ai.impl.SimpleChessHeuristic;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.GameLogic;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoard;
import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoardLayerUtil;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.players.Player;
import org.ucdenver.leesw.ai.systems.Event;
import org.ucdenver.leesw.ai.systems.EventManager;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by william.lees on 9/10/15.
 */
public class PlayerAI implements Player {
    // Depth of AI search
    private static final int MAX_SEARCH_DEPTH = 9;

    private static Logger logger = LogManager.getLogger(PlayerAI.class);

    // Search information
    private int nodesSearched;

    // Team
    private boolean team;

    // AI Stuff
    private MoveGenerator moveGenerator;
    private MinimaxTree moveTree;

    public PlayerAI(boolean team) {
        this.team = team;
        this.moveGenerator = new ChessMoveGenerator();
    }

    @Override
    public Board getNextMove() {
        // Clear out any previous data
        if (moveTree != null) {
            MinimaxTreeControl.clear(moveTree);
        }

        // Create the root node
        moveTree = new MinimaxNode(new ChessBitBoard(GameLogic.getGame().getBoard()));

        this.nodesSearched = 0;
        this.generateSubTree(moveTree, MAX_SEARCH_DEPTH, Short.MIN_VALUE, Short.MAX_VALUE, true);

        // Send message that tree has been generated
        EventManager eventManager = EventManager.getEventManager();
        Event treeGeneratedEvent = new Event(Event.EVENT_TYPE_TREE_BUILT);
        treeGeneratedEvent.setData(moveTree);
        eventManager.addEvent(treeGeneratedEvent);

        // Get children and then release tree to free space
        List<MinimaxNode> children = moveTree.getChildren();
        int value = moveTree.getValue();
        moveTree = null;


        // Check if there are no more moves
        if (children == null || children.size() == 0) {
            logger.info("No more possible moves");
            System.exit(0);
        }

        ArrayList<MinimaxNode> choices = new ArrayList<>();
        for (MinimaxNode child : children) {
            if (value == child.getValue()) {
                choices.add(child);
            }
        }

        // Return resulting board
        MinimaxNode choice = choices.get(ThreadLocalRandom.current().nextInt(choices.size()));
        choice.choose();
        return new ChessBitBoard(choice.getData());
    }

    private boolean gameOver(MinimaxTree tree) {
        return (ChessBitBoardLayerUtil.getPopulationCount(tree.getData().getPiecesOfType(Piece.BLACK_KING)) +
                ChessBitBoardLayerUtil.getPopulationCount(tree.getData().getPiecesOfType(Piece.WHITE_KING)) == 1);
    }

    private short generateSubTree(MinimaxTree root, int depth, short α, short β, boolean maximize) {
        // Increment the nodes searched
        ++this.nodesSearched;

        // Check for base cases
        if (depth == 0 || gameOver(root)) {
            short value = SimpleChessHeuristic.generateValue(root.getData(), this.team);
            root.setValue(value);
            return value;
        }

        // Generate the child states
        List<Board> children = this.generateChildStates(root.getData(), (maximize ? team : !team));

        // Leaf node
        if (children.size() == 0) {
            short value = SimpleChessHeuristic.generateValue(root.getData(), this.team);
            root.setValue(value);
            return value;
        }

        // Add the child states to the tree
        for (byte i = 0; i < children.size(); ++i) {
            Board child = children.get(i);

            // Add the node to the tree
            MinimaxNode childNode = new MinimaxNode(child);
            MinimaxTreeControl.addNode(childNode, root);
        }

        // Check if we're maximizing on this move
        short value;
        if (maximize) {
            value = Short.MIN_VALUE;

            for (byte i = 0; i < root.getChildren().size(); ++i) {

                MinimaxNode childNode = root.getChildren().get(i);

                value = (short) Math.max(value, generateSubTree(childNode, depth - 1, α, β, false));
                α = (short) Math.max(value, α);

                // Check for beta cutoff
                if (β <= α) {
                    break;
                }
            }
        } else {
            value = Short.MAX_VALUE;

            for (byte i = 0; i < root.getChildren().size(); ++i) {

                MinimaxNode childNode = root.getChildren().get(i);

                value = (short) Math.min(value, generateSubTree(childNode, depth - 1, α, β, true));
                β = (short) Math.min(value, β);

                // Check for alpha cutoff
                if (β <= α) {
                    break;
                }
            }
        }

        root.setValue(value);
        return value;
    }

    @Override
    public int getNodesSearched() {
        return this.nodesSearched;
    }

    private List<Board> generateChildStates(Board board, boolean team) {
        List<Board> result = new ArrayList<>();

        // Generate result boards
        List<Move> legalMoves = this.moveGenerator.generateMoves(board, team);

        for (Move legalMove : legalMoves) {
            Board possible = new ChessBitBoard(board);
            possible.applyMove(legalMove);
            result.add(possible);
        }

        // Sanity check
        if (legalMoves.size() != result.size()) {
            logger.error("No the same amount of child states as legal moves");
        }

        return result;
    }

}
