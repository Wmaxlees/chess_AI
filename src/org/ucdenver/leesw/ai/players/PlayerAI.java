package org.ucdenver.leesw.ai.players;

import javafx.util.Pair;
import org.ucdenver.leesw.ai.ai.impl.ChessMoveGeneratorOld;
import org.ucdenver.leesw.ai.ai.impl.MoveOld;
import org.ucdenver.leesw.ai.ai.impl.SimpleChessHeuristic;
import org.ucdenver.leesw.ai.board.impl.BoardOld;
import org.ucdenver.leesw.ai.board.Coordinates;
import org.ucdenver.leesw.ai.Game;
import org.ucdenver.leesw.ai.pieces.Team;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.ai.collections.MinimaxNode;
import org.ucdenver.leesw.ai.ai.*;

import java.util.ArrayList;
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
    private MinimaxNode moveTree;

    public PlayerAI(boolean team) {
        this.team = team;
        this.moveGenerator = new ChessMoveGeneratorOld();
        this.heurisitic = new SimpleChessHeuristic();
    }

    @Override
    public BoardOld getNextMove() {

        // Create the root node
        moveTree = new MinimaxNode(new BoardOld(Game.getGame().getBoard()));

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
        MinimaxNode.clear(moveTree);

        // Return resulting board
        return new BoardOld(result.getData());
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
            List<BoardOld> children = this.generateChildStates(root.getData(), team);

            // Process the child states
            for (BoardOld child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxNode.addNode(childNode, root);

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
            List<BoardOld> children = this.generateChildStates(root.getData(), (team == Team.BLACK) ? Team.WHITE : Team.BLACK);

            // Process the child states
            for (BoardOld child : children) {
                // Add the node to the tree
                MinimaxNode childNode = new MinimaxNode(child);
                MinimaxNode.addNode(childNode, root);

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

    private List<BoardOld> generateChildStates(BoardOld state, boolean turn) {
        ArrayList<BoardOld> subStates = new ArrayList<>();

        ArrayList<MoveOld> moves = new ArrayList<>();

        for (Pair<Coordinates, Byte> entry : state.getPieces()) {

            try {
                byte piece = entry.getValue();
                Coordinates coordinates = entry.getKey();

                // Check if the piece is on the current players team before generating moves
                if (Piece.getColor(piece) == turn) {
                    List<MoveOld> newMoves = moveGenerator.generateMoves(coordinates.getX(), coordinates.getY(), entry.getValue());
                    moves.addAll(newMoves);
                }

            } catch (UnknownPieceException e) {
                System.out.println("Unknown Piece for " + moveGenerator.getClass());
                System.exit(1);
            }
        }


        if (moves != null) {
            for (MoveOld move : moves) {
                if (!move.isCaptureOnly() && !move.isFreeSpaceOnly()) {
                    BoardOld board = new BoardOld(state);

                    board.removePiece(move.getStartX(), move.getStartY());
                    board.addPiece(move.getTargetX(), move.getTargetY(), move.getPiece());

                    subStates.add(board);
                } else if (move.isCaptureOnly() && state.getPiece(move.getTargetX(), move.getTargetY()) != null) {
                    BoardOld board = new BoardOld(state);

                    board.removePiece(move.getStartX(), move.getStartY());
                    board.addPiece(move.getTargetX(), move.getTargetY(), move.getPiece());

                    subStates.add(board);
                } else if (move.isFreeSpaceOnly() && state.getPiece(move.getTargetX(), move.getTargetY()) == null) {
                    BoardOld board = new BoardOld(state);

                    board.removePiece(move.getStartX(), move.getStartY());
                    board.addPiece(move.getTargetX(), move.getTargetY(), move.getPiece());

                    subStates.add(board);
                }
            }
        }

        return subStates;
    }


}
