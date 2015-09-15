package org.ucdenver.leesw.ai.players;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Board.Coordinates;
import org.ucdenver.leesw.ai.ai.Collections.SearchItem;
import org.ucdenver.leesw.ai.ai.Collections.SearchStack;
import org.ucdenver.leesw.ai.Game;
import org.ucdenver.leesw.ai.Pieces.Color;
import org.ucdenver.leesw.ai.Pieces.Piece;
import org.ucdenver.leesw.ai.ai.Collections.MinimaxNode;
import org.ucdenver.leesw.ai.ai.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by william.lees on 9/10/15.
 */
public class PlayerAI implements Player {
    // Depth of AI search
    private static final int MAX_SEARCH_DEPTH = 9;

    // Team
    private Color team;

    // AI Stuff
    private MoveGenerator moveGenerator;
    private Heurisitic heurisitic;
    private MinimaxNode moveTree;

    public PlayerAI(Color team) {
        this.team = team;
        this.moveGenerator = new ChessMoveGenerator();
        this.heurisitic = new SimpleChessHeuristic();
    }

    @Override
    public Board getNextMove() {

        // Create the root node
        moveTree = new MinimaxNode(Game.getGame().getBoard());

        this.generateSubTree(moveTree, MAX_SEARCH_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        List<MinimaxNode> children = moveTree.getChildren();
        MinimaxNode result = null;
        for (MinimaxNode child : children) {
            if (result == null) {
                result = child;
            } else if (child.getValue() > result.getValue()) {
                result = child;
            }
        }

        return result.getData();

//        // Logging information
//        int movesConsidered = 0;
//
//        // Create the root node
//        moveTree = new MinimaxNode(Game.getGame().getBoard());
//        moveTree.setValue(Integer.MIN_VALUE);
//
//        // Add first item to search stack
//        SearchStack searchOrder = new SearchStack();
//        searchOrder.push(moveTree, 0);
//
//
//
//        // Generate each layer of the tree up to the max depth
//        while (searchOrder.size() > 0) {
//            // Get the next node
//            SearchItem current = searchOrder.pop();
//
//            // Make sure this isn't the max depth
//            if (current.getDepth() > MAX_SEARCH_DEPTH) {
//                continue;
//            }
//
//            if (current.getDepth() % 2 == 0) {
//                // Player Chooses Maximum Child
//            } else {
//                // Player Chooses Minimum Child
//            }
//
//            // Generate node's children
//            List<Board> children = this.generateChildStates(current.getNode().getData());
//
//            // Process children
//            for (Board child : children) {
//                ++movesConsidered;
//
//                // Add to tree and stack
//                MinimaxNode childNode = new MinimaxNode(child);
//                MinimaxNode.addNode(childNode, current.getNode());
//                if (current.getDepth() != MAX_SEARCH_DEPTH) {
//                    searchOrder.push(childNode, current.getDepth() + 1);
//                } else {
//                    childNode.setValue(heurisitic.generateValue(child));
//                }
//            }
//        }
//
//        // Choose the best coarse
//        MinimaxNode target = moveTree;
//        if (moveTree.getChildren() != null) {
//            for (MinimaxNode child : moveTree.getChildren()) {
//                if (child.getValue() == target.getValue()) {
//                    target = child;
//                    break;
//                }
//            }
//        }
//
//        System.out.println("Moves Considered: " + movesConsidered);
//
//        return target.getData();
    }

    private int generateSubTree(MinimaxNode root, int depth, int α, int β, boolean max) {
        // Check for base cases
        if (depth == 0) {
            int value = heurisitic.generateValue(root, this.team);
            root.setValue(value);
            return value;
        }

        // Check if we're maximizing on this move
        if (max) {
            int value = Integer.MIN_VALUE;

            // Generate the child states
            List<Board> children = this.generateChildStates(root.getData(), team);

            // Process the child states
            for (Board child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxNode.addNode(childNode, root);

                // Generate subtree for node
                value = Math.max(value, generateSubTree(childNode, depth-1, α, β, false));
                α = Math.max(value, α);

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
            int value = Integer.MAX_VALUE;

            // Generate the child states
            List<Board> children = this.generateChildStates(root.getData(), (team == Color.BLACK) ? Color.WHITE : Color.BLACK);

            // Process the child states
            for (Board child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxNode.addNode(childNode, root);

                value = Math.min(value, generateSubTree(childNode, depth-1, α, β, true));
                β = Math.min(value, β);

                // Check for alpha cutoff
                if (β <= α) {
                    break;
                }
            }

            root.setValue(value);
            return value;
        }
    }

    private List<Board> generateChildStates(Board state, Color turn) {
        ArrayList<Board> subStates = new ArrayList<>();

        ArrayList<Move> moves = new ArrayList<>();

        for (Map.Entry<Coordinates, Piece> entry : state.getPieces().entrySet()) {

            try {
                Piece piece = entry.getValue();
                Coordinates coordinates = entry.getKey();

                // Check if the piece is on the current players team before generating moves
                if (piece.getColor() == turn) {
                    List<Move> newMoves = moveGenerator.generateMoves(coordinates.getX(), coordinates.getY(), entry.getValue());
                    moves.addAll(newMoves);
                }

            } catch (UnknownPieceException e) {
                System.out.println("Unknown Piece for " + moveGenerator.getClass());
                System.exit(1);
            }
        }


        if (moves != null) {
            for (Move move : moves) {
                Board board = new Board(state);

                board.removePiece(move.getStartX(), move.getStartY());

                Piece piece = null;
                try {
                    piece = move.getPiece().getClass().newInstance();
                    piece.setColor(move.getPiece().getColor());

                } catch (Exception e) {
                    System.out.println("Could not generate new instance of piece class");
                    System.exit(-1);
                }

                board.addPiece(move.getTargetX(), move.getTargetY(), piece);

                subStates.add(board);
            }
        }

        return subStates;
    }


}
