package com.bbdr.chess;

import java.util.HashMap;

public class Knight extends Piece implements Renderable, Moveable {
    
    /** Cache for determining if a move is valid. */
    public static final HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
    
    /** Determines if we use the cache or not. */
    private static boolean defaultUseCache = false;
    
    // First time this class is used, create a cache so that
    // we can avoid more expensive computations.
    static {
        // Populate the valid moves cache.
        generateValidMoves(validMoves, new Knight());
        
        // We have populated the cache. Use this by default now.
        defaultUseCache = true;
    }
    
    /**
     * Returns whether the Knight can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @param useCache whether or not we should use the valid moves cache.
     * @return true if the Knight can move to this relative location.
     */
    public boolean isValidMove(int relX, int relY, boolean useCache) {
        if (useCache) {
            return validMoves.get(Position.getRelativeOffset(relX, relY));
        }
        // Get the absolute values for relX and relY
        relY = Math.abs(relY);
        relX = Math.abs(relX);
        
        // Valid moves for the Knight:
        if (relX == 2 || relY == 2) {
            if (relX == 1 || relY == 1) {
                return true;
            }
        }
        // The movement matched none of the valid patterns;
        // this move is not valid.
        return false;
    }
   
    @Override
    public boolean isValidMove(int relX, int relY) {
        return this.isValidMove(relX, relY, defaultUseCache);
    }

    @Override
    public boolean canMoveTo(int relX, int relY, Board board) {   
        return true;
    }
    
    @Override
    public void draw() {
        // TODO
    }
    
    @Override
    public int getRank() {
        return Piece.RANK_KNIGHT;
    }
    
    public Knight(int x, int y, int player) {
        super(x, y, player);
    }
    
    public Knight(int x, int y) {
        super(x, y);
    }
    
    public Knight() {
        super();
    }
}