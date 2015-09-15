package org.ucdenver.leesw.ai.players.impl;

import org.ucdenver.leesw.ai.ai.*;
import org.ucdenver.leesw.ai.ai.collections.MinimaxTreeImpl;
import org.ucdenver.leesw.ai.ai.impl.ChessMoveGenerator;
import org.ucdenver.leesw.ai.ai.impl.SimpleChessHeuristic;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.GameLogic;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoard;
import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;
import org.ucdenver.leesw.ai.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by william.lees on 9/10/15.
 */
public class PlayerAI implements Player {
    // Depth of AI search
    private static final int MAX_SEARCH_DEPTH = 11;

    // Team
    private boolean team;

    // AI Stuff
    private MoveGenerator moveGenerator;
    private Heurisitic heurisitic;
    private MinimaxTree moveTree;

    public PlayerAI(boolean team) {
        this.team = team;
        this.moveGenerator = new ChessMoveGenerator();
        this.heurisitic = new SimpleChessHeuristic();
    }

    @Override
    public Board getNextMove() {

        // Create the root node
        moveTree = new MinimaxTreeImpl(new ChessBitBoard(GameLogic.getGame().getBoard()));

        this.generateSubTree(moveTree, MAX_SEARCH_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        List<MinimaxNode> children = moveTree.getChildren();

        // Check if there are no more moves
        if (children.size() == 0) {
            System.out.println("No more possible moves");
            System.exit(0);
        }

        MinimaxNode result = null;
        for (MinimaxNode child : children) {
            if (result == null) {
                result = child;
            } else if (child.getValue() > result.getValue()) {
                result = child;
            }
        }

        // Clear out Hash memory
        MinimaxTreeControl.clear(moveTree);

        // Return resulting board
        return new ChessBitBoard(result.getData());
    }

    private int generateSubTree(MinimaxTree root, int depth, int α, int β, boolean max) {
        // Check for base cases
        if (depth == 0) {
            int value = heurisitic.generateValue(root.getData(), this.team);
            root.setValue(value);
            return value;
        }

        // Check if we're maximizing on this move
        if (max) {
            int value = Integer.MIN_VALUE;

            // Generate the child states
            Collection<Board> children = this.generateChildStates(root.getData(), team);

            // Process the child states
            for (Board child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxTreeControl.addNode(childNode, root);

                // Generate subtree for node
                value = Math.max(value, generateSubTree(childNode, depth-1, α, β, false));
                α = Math.max(value, α);

                // Check for beta cutoff
                if (β <= α) {
                    // Clear the subtree to free space
                    if (root.getChildren() != null) {
                        root.getChildren().clear();
                    }

                    break;
                }
            }

            root.setValue(value);
            return value;
        }

        // Otherwise we're trying to minimize on this move
        else {
            int value = Integer.MAX_VALUE;

            // Generate the child states
            Collection<Board> children = this.generateChildStates(root.getData(), !team);

            // Process the child states
            for (Board child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxTreeControl.addNode(childNode, root);

                value = Math.min(value, generateSubTree(childNode, depth-1, α, β, true));
                β = Math.min(value, β);

                // Check for alpha cutoff
                if (β <= α) {
                    // Clear the subtree to free space
                    if (root.getChildren() != null) {
                        root.getChildren().clear();
                    }

                    break;
                }
            }

            root.setValue(value);
            return value;
        }
    }

    private Collection<Board> generateChildStates(Board board, boolean team) {
        ArrayList<Board> result = new ArrayList<>();

        // Generate result boards
        Board possible = null;
        for (Move legalMove : this.moveGenerator.generateMoves(board, team)) {
            possible = new ChessBitBoard(board);
            possible.applyMove(legalMove);
            result.add(possible);
        }

        return result;
    }
}
