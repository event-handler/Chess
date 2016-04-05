package com.bbdr.chess;

public interface Moveable {
    
    public boolean canMoveTo(int x, int y);
    public void moveTo(int x, int y);
}