package org.ucdenver.leesw.ai.board;

/**
 * Created by william.lees on 9/15/15.
 */
public interface BitBoardLayer {

    void addPiece(int x, int y);
    void addPiece(long mask);
    void removePiece(int x, int y);
    void removePiece(long mask);

    boolean isPiece(int x, int y);

    long getBoard();
    void setBoard(long data);

    byte getPopulationCount();

    static long and(BitBoardLayer lvalue, BitBoardLayer rvalue) {
        return lvalue.getBoard() & rvalue.getBoard();
    }

    static long or(BitBoardLayer lvalue, BitBoardLayer rvalue) {
        return lvalue.getBoard() | rvalue.getBoard();
    }
}
