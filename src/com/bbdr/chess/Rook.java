package com.bbdr.chess;

public class Rook extends Piece implements Renderable, Moveable {
    
    /**
     * Returns whether the Rook can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @return true if the Rook can move to this relative location.
     */
    @Override
    public boolean isValidMove(int relX, int relY) {
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
    public void draw() {
        // TODO
    }
    
    @Override
    public boolean canMoveTo(int x , int y, Board board) {
        // TODO
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
