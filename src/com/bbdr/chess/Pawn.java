package com.bbdr.chess;

public class Pawn extends Piece implements Moveable, Renderable {
    
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

