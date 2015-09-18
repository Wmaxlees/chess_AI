package org.ucdenver.leesw.ai.players.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.ucdenver.leesw.ai.ai.*;
import org.ucdenver.leesw.ai.ai.collections.MinimaxTree;
import org.ucdenver.leesw.ai.ai.collections.MinimaxTreeControl;
import org.ucdenver.leesw.ai.ai.impl.ChessMoveGenerator;
import org.ucdenver.leesw.ai.ai.impl.SimpleChessHeuristic;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.GameLogic;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoard;
import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;
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
    private static final int MAX_SEARCH_DEPTH = 2;

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
        if (tree.getData().getPiecesOfType(Piece.BLACK_KING) == 0b00L && tree.getData().getPiecesOfType(Piece.WHITE_KING) != 0b00L)
            return true;
        if (tree.getData().getPiecesOfType(Piece.BLACK_KING) != 0b00L && tree.getData().getPiecesOfType(Piece.WHITE_KING) == 0b00L)
            return true;

        return false;
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

        // Check if we're maximizing on this move
        if (maximize) {
            short value = Short.MIN_VALUE;

            // Generate the child states
            Collection<Board> children = this.generateChildStates(root.getData(), team);

            // Leaf node
            if (children.size() == 0) {
                value = SimpleChessHeuristic.generateValue(root.getData(), this.team);
                return value;
            }

            // Process the child states
            for (Board child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxTreeControl.addNode(childNode, root);

                // Generate subtree for node
                value = (short) Math.max(value, generateSubTree(childNode, depth-1, α, β, false));
                α = (short) Math.max(value, α);

                // Check for beta cutoff
                if (β <= α) {
                    break;
                }
            }

            root.setValue(value);
            return value;
        }

        // Otherwise we're trying to minimize on this move
        else {
            short value = Short.MAX_VALUE;

            // Generate the child states
            Collection<Board> children = this.generateChildStates(root.getData(), !team);

            // Leaf node
            if (children.size() == 0) {
                value = SimpleChessHeuristic.generateValue(root.getData(), this.team);
                return value;
            }

            // Process the child states
            for (Board child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxTreeControl.addNode(childNode, root);

                value = (short) Math.min(value, generateSubTree(childNode, depth-1, α, β, true));
                β = (short) Math.min(value, β);

                // Check for alpha cutoff
                if (β <= α) {
                    break;
                }
            }

            root.setValue(value);
            return value;
        }
    }

    @Override
    public int getNodesSearched() {
        return this.nodesSearched;
    }

    private Collection<Board> generateChildStates(Board board, boolean team) {
        ArrayList<Board> result = new ArrayList<>();

        // Generate result boards
        Collection<Move> legalMoves = this.moveGenerator.generateMoves(board, team);

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
