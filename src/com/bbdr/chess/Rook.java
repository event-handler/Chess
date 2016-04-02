package com.bbdr.chess;

public class Rook extends Piece implements Renderable, Moveable {
    
    @Override
    public void draw() {
        // TODO
    }
    
    @Override
    public boolean canMoveTo(int x , int y) {
        // TODO
        return true;
    }
    
    @Override
    public int getRank() {
        return Piece.RANK_ROOK;
    }
    
    public Rook(int x, int y) {
        super(x, y);
    }
    
    public Rook() {
        super();
    }
}
