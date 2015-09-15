package org.ucdenver.leesw.ai;

import org.ucdenver.leesw.ai.Board.Board;
import org.ucdenver.leesw.ai.Pieces.Color;
import org.ucdenver.leesw.ai.Pieces.King;
import org.ucdenver.leesw.ai.Pieces.Pawn;
import org.ucdenver.leesw.ai.ai.Heurisitic;
import org.ucdenver.leesw.ai.ai.SimpleChessHeuristic;
import org.ucdenver.leesw.ai.players.Player;
import org.ucdenver.leesw.ai.players.PlayerAI;

/**
 * Created by william.lees on 9/10/15.
 */
public class Game {
    private Board           currentState;
    private boolean         playing;
    private Color           turn;
    private Player          playerWhite;
    private Player          playerBlack;

    private Heurisitic      heuristic;

    private static Game     _singleton;

    public static Game getGame() {
        if (_singleton == null) {
            _singleton = new Game();
        }

        return _singleton;
    }

    private Game() {
        // Initialize the game state
        this.currentState = new Board();
        this.turn = Color.WHITE;
        this.playing = true;

        // Create the players
        this.playerWhite = new PlayerAI(Color.WHITE);
        this.playerBlack = new PlayerAI(Color.BLACK);

        // Set initial state of board
        King blackKing = new King();                    // Black King
        blackKing.setColor(Color.BLACK);
        currentState.addPiece(1, 6, blackKing);

        King whiteKing = new King();                    // White King
        whiteKing.setColor(Color.WHITE);
        currentState.addPiece(8, 8, whiteKing);

        Pawn blackPawn = new Pawn();                    // Black Pawn
        blackPawn.setColor(Color.BLACK);
        currentState.addPiece(8, 5, blackPawn);

        Pawn whitePawn = new Pawn();                    // White Pawn
        whitePawn.setColor(Color.WHITE);
        currentState.addPiece(3, 6, whitePawn);

        heuristic = new SimpleChessHeuristic();
    }

    @Override
    public String toString() {
        return currentState.toString();
    }

    public boolean isOver() {
        return !playing;
    }

    public void takeTurn() {
        if (turn == Color.WHITE) {
            this.currentState = this.playerWhite.getNextMove();
            turn = Color.BLACK;
            System.out.println("White's Turn");
        } else {
            this.currentState = this.playerBlack.getNextMove();
            turn = Color.WHITE;
            System.out.println("Black's Turn");
        }
    }

    public Board getBoard() {
        return this.currentState;
    }
}
