package com.bbdr.chess;

import java.util.HashMap;

public class Rook extends Piece implements Renderable, Moveable {
    
    /** Cache for determining if a move is valid. */
    public static final HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
    
    /** Determines if we use the cache or not. */
    private static boolean defaultUseCache = false;
    
    // First time this class is used, create a cache so that
    // we can avoid more expensive computations.
    static {
        // Populate the valid moves cache.
        generateValidMoves(validMoves, new Rook());
        
        // We have populated the cache. Use this by default now.
        defaultUseCache = true;
    }
    
    /**
     * Returns whether the Rook can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @param useCache whether or not we should use the valid moves cache.
     * @return true if the Rook can move to this relative location.
     */
    @Override
    public boolean isValidMove(int relX, int relY, boolean useCache) {
        if (useCache) {
            return validMoves.get(Position.getRelativeOffset(relX, relY));
        }
        // Valid moves for the Rook:
        // (1)Horizontal, (2)Vertical.
        if (relX == 0 || relY == 0) {
            // We are on one of the axes, so this move is either
            // horizontal or vertical, unless relX = relY = 0.
            // If relX == relY then both are 0. Thus, the validity
            // of this move depends on relX NOT being equal to relY.
            return (relX != relY);
        }
        // If we've reached this point, this move is not valid.
        return false;
    }
    
    @Override
    public boolean isValidMove(int relX, int relY) {
        return this.isValidMove(relX, relY, defaultUseCache);
    }

    @Override
    public void draw() {
        // TODO
    }
    
    @Override
    public boolean canMoveTo(int x , int y, Board board) {
        // There are no special conditions that this Piece requires.
        // All inputs will be valid.
        return true;
    }
    
    @Override
    public int getRank() {
        return Piece.RANK_ROOK;
    }
    
    public Rook(int x, int y, int player) {
        super(x, y, player);
    }
    
    public Rook(int x, int y) {
        super(x, y);
    }
    
    public Rook() {
        super();
    }
}
