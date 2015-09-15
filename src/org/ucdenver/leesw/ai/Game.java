package org.ucdenver.leesw.ai;

import org.ucdenver.leesw.ai.board.impl.BoardOld;
import org.ucdenver.leesw.ai.pieces.Team;
import org.ucdenver.leesw.ai.pieces.Piece;
import org.ucdenver.leesw.ai.players.Player;
import org.ucdenver.leesw.ai.players.PlayerAI;

/**
 * Created by william.lees on 9/10/15.
 */
public class Game {
    private BoardOld currentState;
    private boolean         playing;
    private boolean         turn;

    private Player          playerWhite;
    private Player          playerBlack;

    private static Game     _singleton;

    public static Game getGame() {
        if (_singleton == null) {
            _singleton = new Game();
        }

        return _singleton;
    }

    private Game() {
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

    public void takeTurn() {
        if (turn == Team.WHITE) {
            this.currentState = this.playerWhite.getNextMove();
            turn = Team.BLACK;
            System.out.println("White's Turn");
        } else {
            this.currentState = this.playerBlack.getNextMove();
            turn = Team.WHITE;
            System.out.println("Black's Turn");
        }
    }

    public BoardOld getBoard() {
        return this.currentState;
    }

    private void setupGame(GameType gameType) {
        this.currentState = new BoardOld();

        if (gameType == GameType.PAWN_ONLY) {
            currentState.addPiece(1, 2, Piece.WHITE_PAWN);
            currentState.addPiece(2, 2, Piece.WHITE_PAWN);
            currentState.addPiece(3, 2, Piece.WHITE_PAWN);
            currentState.addPiece(4, 2, Piece.WHITE_PAWN);
            currentState.addPiece(5, 2, Piece.WHITE_PAWN);
            currentState.addPiece(6, 2, Piece.WHITE_PAWN);
            currentState.addPiece(7, 2, Piece.WHITE_PAWN);
            currentState.addPiece(8, 2, Piece.WHITE_PAWN);

            currentState.addPiece(1, 7, Piece.BLACK_PAWN);
            currentState.addPiece(2, 7, Piece.BLACK_PAWN);
            currentState.addPiece(3, 7, Piece.BLACK_PAWN);
            currentState.addPiece(4, 7, Piece.BLACK_PAWN);
            currentState.addPiece(5, 7, Piece.BLACK_PAWN);
            currentState.addPiece(6, 7, Piece.BLACK_PAWN);
            currentState.addPiece(7, 7, Piece.BLACK_PAWN);
            currentState.addPiece(8, 7, Piece.BLACK_PAWN);
        } else if (gameType == GameType.R_RETI_ENDGAME) {
            currentState.addPiece(1, 6, Piece.BLACK_KING);
            currentState.addPiece(8, 8, Piece.WHITE_KING);

            currentState.addPiece(8, 5, Piece.BLACK_PAWN);
            currentState.addPiece(3, 6, Piece.WHITE_PAWN);
        }
    }

    private enum GameType {
        PAWN_ONLY,
        R_RETI_ENDGAME
    }
}
