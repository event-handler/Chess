package com.bbdr.chess;

public class Knight extends Piece implements Renderable, Moveable {
    
    // One of these days someone is going to write this method.
    // Maybe tomorrow?
    @Override
    public boolean canMoveTo(int x, int y, Board board) {
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