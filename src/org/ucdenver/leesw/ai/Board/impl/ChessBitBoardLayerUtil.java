package org.ucdenver.leesw.ai.board.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ucdenver.leesw.ai.BitUtilities;

/**
 * Created by william.lees on 9/15/15.
 */
public class ChessBitBoardLayerUtil {

    private static Logger logger = LogManager.getLogger(ChessBitBoardLayerUtil.class);

    public static long addPiece(long board, int x, int y) {
        return (board | BitUtilities.getLocationMask(x, y));
    }

    public static long addPiece(long board, long pieceLocation) {
        return board | pieceLocation;
    }

    public static long removePiece(long board, int x, int y) {
        return (board & ~BitUtilities.getLocationMask(x, y));
    }

    public static long removePiece(long board, long pieceLocation) {
        return (board & ~pieceLocation);
    }

    public static boolean isPiece(long board, int x, int y) {
        return (board & BitUtilities.getLocationMask(x, y)) != 0b00L;
    }

    public static boolean isPiece(long board, long pieceLocation) {
        return (board & pieceLocation) != 0b00L;
    }

    public static byte getPopulationCount(long board) {
        byte pop = 0;

        while (board != 0) {
            ++pop;
            board &= board - 1; // Remove the lowest bit
        }

        return pop;
    }

    public static long getEmptyBoard() {
        return 0b00L;
    }
}
