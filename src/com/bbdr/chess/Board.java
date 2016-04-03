package com.bbdr.chess;

public class Board {
    
    public Piece[] tiles = new Piece[8 * 8];
    
    public Piece selected = null;
    
    public void add(Piece p) {
        int offset = p.position.getAbsoluteOffset();
        if (tiles[offset] != null) {
            // There is a piece at this offset.
        }
        tiles[offset] = p;
    }
    
    public void selectAt(int x, int y) {
        selected = this.get(x, y);
    }
    
    public Piece get(int x, int y) {
        return tiles[Position.getAbsoluteOffset(x,  y)];
    }
}