package org.ucdenver.leesw.ai;

import javafx.scene.control.TreeView;
import org.ucdenver.leesw.ai.ai.MinimaxTree;
import org.ucdenver.leesw.ai.board.Board;
import org.ucdenver.leesw.ai.board.impl.ChessBitBoard;
import org.ucdenver.leesw.ai.pieces.Team;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.players.Player;
import org.ucdenver.leesw.ai.players.impl.PlayerAI;

/**
 * Created by william.lees on 9/10/15.
 */
public class GameLogic implements Runnable {
    private Board           currentState;
    private boolean         playing;
    private boolean         turn;

    private Player          playerWhite;
    private Player          playerBlack;

    private static GameLogic _singleton;

    public static GameLogic getGame() {
        if (_singleton == null) {
            _singleton = new GameLogic();
        }

        return _singleton;
    }

    private GameLogic() {
        // Initialize the game state
        this.turn = Team.WHITE;
        this.playing = true;

        // Create the players
        this.playerWhite = new PlayerAI(Team.WHITE);
        this.playerBlack = new PlayerAI(Team.BLACK);

        // Set initial state of board
        this.setupGame(GameType.R_RETI_ENDGAME);
    }

    @Override
    public String toString() {
        return currentState.toString();
    }

    public boolean isOver() {
        return !playing;
    }

    public void run() {
        if (turn == Team.WHITE) {
            this.currentState = this.playerWhite.getNextMove();
            turn = Team.BLACK;
        } else {
            this.currentState = this.playerBlack.getNextMove();
            turn = Team.WHITE;
        }
    }

    public int getNodesSearchedLastMove() {
        int nodesSearched;

        if (turn == Team.WHITE) {
            nodesSearched = this.playerBlack.getNodesSearched();
        } else {
            nodesSearched = this.playerWhite.getNodesSearched();
        }

        return nodesSearched;
    }

//    public void generateTreeFromLastMove(TreeView<MinimaxTree> treeView) {
//        if (turn == Team.WHITE) {
//            this.playerBlack.generateSearchTreeView(treeView);
//        } else {
//            this.playerWhite.generateSearchTreeView(treeView);
//        }
//    }

    public Board getBoard() {
        return this.currentState;
    }

    private void setupGame(GameType gameType) {
        this.currentState = new ChessBitBoard();

        if (gameType == GameType.PAWN_ONLY) {
            currentState.addPiece(2, 1, Piece.WHITE_PAWN);
            currentState.addPiece(2, 2, Piece.WHITE_PAWN);
            currentState.addPiece(2, 3, Piece.WHITE_PAWN);
            currentState.addPiece(2, 4, Piece.WHITE_PAWN);
            currentState.addPiece(2, 5, Piece.WHITE_PAWN);
            currentState.addPiece(2, 6, Piece.WHITE_PAWN);
            currentState.addPiece(2, 7, Piece.WHITE_PAWN);
            currentState.addPiece(2, 8, Piece.WHITE_PAWN);

            currentState.addPiece(7, 1, Piece.BLACK_PAWN);
            currentState.addPiece(7, 2, Piece.BLACK_PAWN);
            currentState.addPiece(7, 3, Piece.BLACK_PAWN);
            currentState.addPiece(7, 4, Piece.BLACK_PAWN);
            currentState.addPiece(7, 5, Piece.BLACK_PAWN);
            currentState.addPiece(7, 6, Piece.BLACK_PAWN);
            currentState.addPiece(7, 7, Piece.BLACK_PAWN);
            currentState.addPiece(7, 8, Piece.BLACK_PAWN);

        } else if (gameType == GameType.R_RETI_ENDGAME) {
            currentState.addPiece(6, 1, Piece.BLACK_KING);
            currentState.addPiece(8, 8, Piece.WHITE_KING);

            currentState.addPiece(5, 8, Piece.BLACK_PAWN);
            currentState.addPiece(6, 3, Piece.WHITE_PAWN);
        }
    }

    private enum GameType {
        PAWN_ONLY,
        R_RETI_ENDGAME
    }
}
