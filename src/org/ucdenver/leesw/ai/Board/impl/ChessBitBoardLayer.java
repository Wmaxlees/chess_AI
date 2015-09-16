package org.ucdenver.leesw.ai.board.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.board.BitBoardLayer;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessBitBoardLayer implements BitBoardLayer {

    private static Logger logger = LogManager.getLogger(ChessBitBoardLayer.class);

    private static final long[/*x*/][/*y*/] MASK =
            {
                    {0b01L <<  0, 0b01L <<  1, 0b01L <<  2, 0b01L <<  3, 0b01L <<  4, 0b01L <<  5, 0b01L <<  6, 0b01L <<  7},
                    {0b01L <<  8, 0b01L <<  9, 0b01L << 10, 0b01L << 11, 0b01L << 12, 0b01L << 13, 0b01L << 14, 0b01L << 15},
                    {0b01L << 16, 0b01L << 17, 0b01L << 18, 0b01L << 19, 0b01L << 20, 0b01L << 21, 0b01L << 22, 0b01L << 23},
                    {0b01L << 24, 0b01L << 25, 0b01L << 26, 0b01L << 27, 0b01L << 28, 0b01L << 29, 0b01L << 30, 0b01L << 31},
                    {0b01L << 32, 0b01L << 33, 0b01L << 34, 0b01L << 35, 0b01L << 36, 0b01L << 37, 0b01L << 38, 0b01L << 39},
                    {0b01L << 40, 0b01L << 41, 0b01L << 42, 0b01L << 43, 0b01L << 44, 0b01L << 45, 0b01L << 46, 0b01L << 47},
                    {0b01L << 48, 0b01L << 49, 0b01L << 50, 0b01L << 51, 0b01L << 52, 0b01L << 53, 0b01L << 54, 0b01L << 55},
                    {0b01L << 56, 0b01L << 57, 0b01L << 58, 0b01L << 59, 0b01L << 60, 0b01L << 61, 0b01L << 62, 0b01L << 63}
            };

    private long board;

    @Override
    public void addPiece(int x, int y) {
        long mask = getMaskOf(x, y);
        this.board |= mask;
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

    private static long getMaskOf(int x, int y) {
        return MASK[x-1][y-1];
    }
}
