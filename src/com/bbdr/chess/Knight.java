package com.bbdr.chess;

public class Knight extends Piece implements Renderable, Moveable {
    
    /**
     * Returns whether the Knight can move to relative coordinates (relX, relY).
     * This method is used as the first step for move validation. Its outputs
     * do not depend on the current configuration of the board. That is, the
     * method returns true if the move to relative (relX, relY) are valid in
     * any board configuration. Because it is fixed, its results should be
     * used to build a cache.
     * @param relX the x-coordinate relative to the piece.
     * @param relY the y-coordinate relative to the piece.
     * @return true if the Knight can move to this relative location.
     */
    
    public boolean isValidMove(int relX, int relY){
           
        if (relY == -2) {
            if (relX == -1 || relX ==  1 ) {
                return true;
            }
        }
        if (relX == 2) {
            if(relY == -1 || relY == 1)
                return true;
        }
        if (relY == 2) {
            if(relX == -1 || relX == 1)
                return true;
        }
        if (relX == -2) {
            if(relY == -1 || relY == 1)
                return true;
        }
        // The movement matched none of the valid patterns;
        // this move is not valid.
        return false;
       
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