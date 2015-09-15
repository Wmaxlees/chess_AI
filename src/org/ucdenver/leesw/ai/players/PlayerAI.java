package org.ucdenver.leesw.ai.players;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Game;
import org.ucdenver.leesw.ai.Pieces.Color;
import org.ucdenver.leesw.ai.Pieces.Piece;
import org.ucdenver.leesw.ai.Tree.BoardNode;
import org.ucdenver.leesw.ai.ai.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by william.lees on 9/10/15.
 */
public class PlayerAI implements Player {
    // Depth of AI search
    private static final int MAX_SEARCH_DEPTH = 3;

    // Team
    private Color team;

    // AI Stuff
    private MoveGenerator moveGenerator;
    private Heurisitic heurisitic;
    private BoardNode moveTree;

    private class BoardPair {
        public int depth;
        public BoardNode data;
    }

    public PlayerAI(Color team) {
        this.team = team;
        this.moveGenerator = new ChessMoveGenerator();
        this.heurisitic = new SimpleChessHeuristic();
    }

    @Override
    // TODO: MEMOIZE THE RESULTS OF THE SEARCH
    public Board getNextMove() {
        // Create the root node
        moveTree = new BoardNode(Game.getGame().getBoard());

        // Create the first item for the stack
        BoardPair first = new BoardPair();
        first.data = moveTree;
        first.depth = 0;

        // Generate each layer of the tree up to the max depth
        Stack<BoardPair> searchOrder = new Stack<>();
        searchOrder.push(first);
        int minValue = Integer.MIN_VALUE;
        while (searchOrder.size() > 0) {
            // Get the next node to expand
            BoardPair current = searchOrder.pop();

            // Print depth
            // System.out.println("Search Depth: " + current.depth);

            // Make sure this isn't the max depth
            if (current.depth > MAX_SEARCH_DEPTH) {
                continue;
            }

            // Generate the list of possibilities and add them to the stack and tree
            List<Board> possibilities = this.generateNextLayer(current.data.getData());
            for (Board possibility : possibilities) {
                // Check if we can cut this off
                if (current.depth == MAX_SEARCH_DEPTH) {
                    if (heurisitic.generateValue(possibility) < minValue) {
                        // System.out.println("Cutoff!!!");
                        break;
                    }
                }

                // Add to stack
                BoardPair pair = new BoardPair();
                pair.data = new BoardNode(possibility);
                pair.depth = current.depth+1;
                searchOrder.push(pair);

                // Add to tree
                current.data.addNode(pair.data);
            }

            // Get the minimum value of the layer
            if (current.depth == MAX_SEARCH_DEPTH) {

                int minOfLayer = Integer.MAX_VALUE;
                for (Board possibility : possibilities) {
                    int boardValue = heurisitic.generateValue(possibility);
                    if (minOfLayer > boardValue)
                        minOfLayer = boardValue;
                }

                // If this layer has a greater minimum, assign the new value
                if (minOfLayer > minValue) {
                    minValue = minOfLayer;
                }

                // Propogate the min value up
                current.data.setValue(minValue);
            }
        }

        // Choose the best coarse
        BoardNode target = moveTree;
        if (moveTree.getChildren() != null) {
            for (BoardNode child : moveTree.getChildren()) {
                if (child.getValue() == target.getValue()) {
                    target = child;
                    break;
                }
            }
        }

        return target.getData();
    }

    private List<Board> generateNextLayer(Board state) {
        ArrayList<Board> subStates = new ArrayList<>();

        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i <= Board.BOARD_HEIGHT; ++i) {
            for (int j = 1; j <= Board.BOARD_WIDTH; ++j) {
                Piece piece = state.getTile(j, i);
                if (piece != null && piece.getColor() == this.team) {
                    try {
                        moves.addAll(moveGenerator.generateMoves(j, i, piece));
                    } catch (UnknownPieceException e) {
                        System.out.println("Unknown Piece for " + moveGenerator.getClass());
                        System.exit(1);
                    }
                }
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
                    piece.setX(move.getTargetX());
                    piece.setY(move.getTargetY());

                } catch (Exception e) {
                    System.out.println("Could not generate new instance of piece class");
                    System.exit(-1);
                }
                board.addPiece(piece);

                subStates.add(board);
            }
        }

        return subStates;
    }


}
