package com.bbdr.chess;

import java.util.HashMap;

public class Queen extends Piece implements Moveable, Renderable {
    
    /** Cache for determining if a move is valid. */
    public static final HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
    
    /** Determines if we use the cache or not. */
    private static boolean defaultUseCache = false;
    
    // First time this class is used, create a cache so that
    // we can avoid more expensive computations.
    static {
        // Populate the valid moves cache.
        generateValidMoves(validMoves, new Queen());
        
        // We have populated the cache. Use this by default now.
        defaultUseCache = true;
    }
    
    /**
     * Returns whether the Queen can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @param useCache whether or not we should use the valid moves cache.
     * @return true if the Queen can move to this relative location.
     */
    @Override
    public boolean isValidMove(int relX, int relY, boolean useCache) {
        if (useCache) {
            return validMoves.get(Position.getRelativeOffset(relX, relY));
        }
        // Valid moves for the Queen:
        // (1)North, (2)East, (3)South, (4)West
        // (5)Northwest, (6)Northeast, (7)Southwest, (8)Southeast.

        // Queen moves are symmetrical. We can use the absolute values
        // and restrict relX and relY to the first quadrant.
        // of relX and relY.
        relY = Math.abs(relY);
        relX = Math.abs(relX);

        // Allow moving on the diagonal axes
        if (relX == relY) {
            // This is a diagonal movement, so it is valid unless the Queen
            // is trying to move to itself (0, 0).
            if (relX != 0) {
                // This move is invalid.
                return true;
            }
        }
        // Allow moving on the horizontal and vertical axes
        if (relX == 0 || relY == 0) {
            // This move is either horizontal or vertical, so it is valid
            // unless the Queen is trying to move to itself (0, 0).
            if (relX != relY) {
                return true;
            }
        }
        // If all else fails the move is not possible
        return false;
    }

    @Override
    public boolean isValidMove(int relX, int relY) {
        return this.isValidMove(relX, relY, defaultUseCache);
    }

    @Override
    public boolean canMoveTo(int x, int y, Board board) {
        // TODO
        return true;
    }

    @Override
    public void draw() {
        // TODO
    }

    @Override
    public int getRank() {
        return Piece.RANK_QUEEN;
    }

    public Queen(int x, int y, int player) {
        super(x, y, player);
    }

    public Queen(int x, int y) {
        super(x, y);
    }

    public Queen() {
        super();
    }
}
