package com.bbdr.chess;

public class Pawn extends Piece implements Moveable, Renderable {
    /**
     * Whether or not this piece has moved.
     * This is used for the en passant move.  
     */
    public boolean hasMoved = false;
    
    /**
     * Returns whether the Pawn can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @return true if the Pawn can move to this relative location.
     */
    public boolean isValidMove(int relX, int relY) {
        // The pawn can only advance. For this, we need to rotate
        // the perspective depending on who is playing.
        // The board is only symmetrical about the x-axis.
        relY = (getPlayer() == PLAYER_BLACK ? -1 : 1) * relY;
        
        // Valid moves for the Pawn:
        // (1)North.
        if (relX == 0 && relY == -1) {
            return true;
        }
        // (2)En Passant
        if (relX == 0 && relY == -2) {
            return true;
        }
        // (3)Capture Northeast, (4)Capture Northwest.
        // Both of these are north movements, so relY must be -1. 
        if (relY == -1) {
            // East is +1 and west is -1.
            if (relX == -1 || relX == 1) {
                return true;
            }
        }
        // The movement matched none of the valid patterns;
        // this move is not valid.
        return false;
    }
    
    @Override
    public boolean canMoveTo(int relX, int relY, Board board) {
        // The pawn can only advance. For this, we need to rotate
        // the perspective depending on who is playing.
        // The board is only symmetrical about the x-axis.
        int factorRelY = (getPlayer() == PLAYER_BLACK ? -1 : 1);
        
        // Apply the perspective factor to rotate.
        relY = factorRelY * relY;
        
        // Handle the special case of en passant.
        if (relX == 0 && relY == -2) {
            // In the case of en passant, the validity
            // of this move depends on the Pawn not having moved.
            return (!this.hasMoved);
        }
        
        // Handle the capture cases.
        if (relY == -1) {
            if (relX == -1 || relX == 1) {
                // In case of capturing, the validity of
                // this move depends on the tile being occupied.
                // NOTE that we use the original input for relY here.
                // The board is not actually rotated!
                return (board.isOccupied(this.position.x + relX, this.position.y + relY * factorRelY));
            }
        }
        // If we reach this point, this is not a special move; it is
        // valid as far as we are concerned right now.
        return true;
    }
    
    @Override
    public void draw() {
        // TODO
    }
    
    @Override
    public int getRank() {
        return Piece.RANK_PAWN;
    }
    
    public Pawn(int x, int y, int player) {
        super(x, y, player);
    }
    
    public Pawn(int x, int y) {
        super(x, y);
    }
    
    public Pawn() {
        super();
    }
}