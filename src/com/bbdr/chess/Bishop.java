package com.bbdr.chess;

public class Bishop extends Piece implements Renderable, Moveable {
    
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
        return Piece.RANK_BISHOP;
    }
    
    public Bishop(int x, int y) {
        super(x, y);
    }
    
    public Bishop() {
        super();
    }
}
