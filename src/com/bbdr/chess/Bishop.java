package com.bbdr.chess;

import java.util.HashMap;

public class Bishop extends Piece implements Renderable, Moveable {

    /** Cache for determining if a move is valid. */
    public static final HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
    
    /** Determines if we use the cache or not. */
    private static boolean defaultUseCache = false;
    
    // First time this class is used, create a cache so that
    // we can avoid more expensive computations.
    static {
        // Populate the valid moves cache.
        generateValidMoves(validMoves, new Bishop());
        
        // We have populated the cache. Use this by default now.
        defaultUseCache = true;
    }
    
    /**
     * Returns whether the Bishop can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @param useCache whether or not we should use the valid moves cache.
     * @return true if the Bishop can move to this relative location.
     */
    @Override
    public boolean isValidMove(int relX, int relY, boolean useCache) {
        if (useCache) {
            return validMoves.get(Position.getRelativeOffset(relX, relY));
        }
        // Valid moves for the Bishop:
        // (1)Diagonal
        
        // Bishop moves are symmetrical about the x- and y-axes.
        // By using the absolute values, we can restrict ourselves
        // to the first quadrant.
        relX = Math.abs(relX);
        relY = Math.abs(relY);
        // Allow moving on the diagonal axes
        if (relX == relY) {
            // This move is diagonal, so it is valid, unless
            // the Bishop is trying to move to itself (0, 0).
            if (relX != 0) {
                // Since relX = relY, neither can be 0.
                return true;
            }
        }
        // If we have reached this point, this move is not valid.
        return false;
    }
    
    public boolean isValidMove(int relX, int relY) {
        return this.isValidMove(relX, relY, defaultUseCache);
    }

    @Override
    public boolean canMoveTo(int x, int y, Board board) {
        // The Bishop has no moves that depend on the Board.
        // All inputs will be valid.
        return true;
    }

    @Override
    public void draw() {
        // TODO
    }

    @Override
    public int getRank() {
        return Piece.RANK_BISHOP;
    }

    public Bishop(int x, int y, int player) {
        super(x, y, player);
    }

    public Bishop(int x, int y) {
        super(x, y);
    }

    public Bishop() {
        super();
    }
}
