package com.bbdr.chess;

import android.util.Log;

public class PieceMovementTest {
    public static void testValidMovements(Moveable piece) {
        if (piece instanceof Piece) {
            Log.d("chessfag", "Testing " + ((Piece)piece).getRankName());
        }
        for (int x = -7; x <= 7; x++) {
            for (int y = -7; y <= 7; y++) {
                if (piece.isValidMove(x, y)) {
                    Log.d("chessfag", "(" + x + ", " + y + ") : valid");
                }
            }
        }
    }

}
