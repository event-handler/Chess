package com.bbdr.chess;

import java.util.HashMap;

public class King extends Piece implements Moveable, Renderable {
    public boolean hasMoved = false;
    
    /** Cache for determining if a move is valid. */
    public static final HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
    
    /** Determines if we use the cache or not. */
    private static boolean defaultUseCache = false;
    
    // First time this class is used, create a cache so that
    // we can avoid more expensive computations.
    static {
        // Populate the valid moves cache.
        generateValidMoves(validMoves, new King());
        
        // We have populated the cache. Use this by default now.
        defaultUseCache = true;
    }
    
    /**
     * Returns whether the King can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @param useCache whether or not we should use the valid moves cache.
     * @return true if the King can move to this relative location.
     */
    @Override
    public boolean isValidMove(int relX, int relY, boolean useCache) {
        if (useCache) {
            return validMoves.get(Position.getRelativeOffset(relX, relY));
        }
        // Valid moves for the King:
        // (1)North, (2)East, (3)South, (4)West
        // (5)Northwest, (6)Northeast, (7)Southwest, (8)Southeast.
        if (-1 <= relX && relX <= 1) {
            // X-offset is valid, is Y-offset?
            if (-1 <= relY && relY <= 1) {
                // Y-offset is valid.
                // This move can be done in some configuration.
                return true;
            }
        }
        
        // (9)Kingside Castling, (10)Queenside Castling.
        // King- and Queen-side Castling are both horizontal moves.
        // So relY must be 0.
        if (relY == 0) {
            // Because the board is not symmetrical about the y-axis,
            // there are only two valid moves: x = -3 and x = +2
            if (relX == 2) {
                // Kingside castling.
                return true;
            }
            if (relX == -3) {
                // Queenside castling.
                return true;
            }
        }
        // If we've reached this point, this move is not valid.
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
        return Piece.RANK_KING;
    }
    
    public King(int x, int y, int player) {
        super(x, y, player);
    }
    
    public King(int x, int y) {
        super(x, y);
    }
    
    public King() {
        super();
    }
}