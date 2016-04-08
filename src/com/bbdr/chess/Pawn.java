package com.bbdr.chess;

public class Pawn extends Piece implements Moveable, Renderable {
    
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
        
    public boolean isValidMove(int relX, int relY){
        // Valid moves for the Pawn:
        // (1)North, (2)2 squares North, (3)NorthEast
        // (4)NorthWest, (5)En Passant
       
        if (relX == 0 && relY == -1) {           
            return true;                
        }    
        if (relX == 0 && relY == -2) {
            return true;
        }             
        if (relY == -1) {
            if (relX == -1 || relX == 1) {
                return true;
            }
        }      
        return false;
    }
    
    // TO DO
    // Allow for pawn promotion
    
      
    @Override
    public boolean canMoveTo(int x, int y) {
        // TODO
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
    
    public Pawn(int x, int y) {
        super(x, y);
    }
    
    public Pawn() {
        super();
    }
}