package com.bbdr.chess;

public class Queen extends Piece implements Moveable, Renderable {
    
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
        return Piece.RANK_QUEEN;
    }
    
    public Queen(int x, int y, int player) {
        super(x, y, player);
    }
    
    public Queen(int x, int y) {
        super(x, y);
    }
    
    public Queen() {
        super();
    }
}
