package org.ucdenver.leesw.ai.board.impl;

import org.ucdenver.leesw.ai.board.BitBoardLayer;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessBitBoardLayer implements BitBoardLayer {
    private static final long[/*x*/][/*y*/] MASK =
            {
                    {0b01 <<  0, 0b01 <<  1, 0b01 <<  2, 0b01 <<  3, 0b01 <<  4, 0b01 <<  5, 0b01 <<  6, 0b01 <<  7},
                    {0b01 <<  8, 0b01 <<  9, 0b01 << 10, 0b01 << 11, 0b01 << 12, 0b01 << 13, 0b01 << 14, 0b01 << 15},
                    {0b01 << 16, 0b01 << 17, 0b01 << 18, 0b01 << 19, 0b01 << 20, 0b01 << 21, 0b01 << 22, 0b01 << 23},
                    {0b01 << 24, 0b01 << 25, 0b01 << 26, 0b01 << 27, 0b01 << 28, 0b01 << 29, 0b01 << 30, 0b01 << 31},
                    {0b01 << 32, 0b01 << 33, 0b01 << 34, 0b01 << 35, 0b01 << 36, 0b01 << 37, 0b01 << 38, 0b01 << 39},
                    {0b01 << 40, 0b01 << 41, 0b01 << 42, 0b01 << 43, 0b01 << 44, 0b01 << 45, 0b01 << 46, 0b01 << 47},
                    {0b01 << 48, 0b01 << 49, 0b01 << 50, 0b01 << 51, 0b01 << 52, 0b01 << 53, 0b01 << 54, 0b01 << 55},
                    {0b01 << 56, 0b01 << 57, 0b01 << 58, 0b01 << 59, 0b01 << 60, 0b01 << 61, 0b01 << 62, 0b01 << 63}
            };

    private long board;

    @Override
    public void addPiece(int x, int y) {
        this.board |= getMaskOf(x, y);
    }

    @Override
    public void addPiece(long mask) {
        this.board |= mask;
    }

    @Override
    public void removePiece(int x, int y) {
        this.board &= ~this.getMaskOf(x, y);
    }

    @Override
    public void removePiece(long mask) {
        this.board &= ~mask;
    }

    @Override
    public boolean isPiece(int x, int y) {
        long mask = this.getMaskOf(x, y);
        return (this.board & mask) == mask;
    }

    @Override
    public long getBoard() {
        return this.board;
    }

    @Override
    public void setBoard(long data) {
        this.board = data;
    }

    @Override
    public byte getPopulationCount() {
        long pieces = this.board;
        byte pop = 0;

        while (pieces != 0) {
            ++pop;
            pieces &= pieces - 1; // Remove the lowest bit
        }

        return pop;
    }

    private int getIndex(int y) {
        return y;
    }

    private static long getMaskOf(int x, int y) {
        return MASK[x-1][y-1];
    }
}
