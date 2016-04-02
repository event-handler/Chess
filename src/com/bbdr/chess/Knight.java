package com.bbdr.chess;

public class Knight extends Piece implements Renderable, Moveable {
    
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
        return Piece.RANK_KNIGHT;
    }
    
    public Knight(int x, int y) {
        super(x, y);
    }
    
    public Knight() {
        super();
    }
}