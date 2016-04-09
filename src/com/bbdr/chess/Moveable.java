package com.bbdr.chess;

public interface Moveable {
    
    public boolean isValidMove(int relX, int relY);
    public boolean canMoveTo(int x, int y, Board board);
    public void moveTo(int x, int y);
    public int getX();
    public int getY();
    public int getPlayer();
}