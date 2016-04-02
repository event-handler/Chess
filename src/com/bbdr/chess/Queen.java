package com.bbdr.chess;

public class Queen extends Piece implements Moveable, Renderable {
    
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
        return Piece.RANK_QUEEN;
    }
    
    public Queen(int x, int y) {
        super(x, y);
    }
    
    public Queen() {
        super();
    }
}
