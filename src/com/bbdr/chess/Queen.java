package com.bbdr.chess;

public class Queen extends Piece implements Moveable, Renderable {
    @Override
    public boolean isValidMove(int relX, int relY) {
        // Valid moves for the Queen:
        // (1)North, (2)East, (3)South, (4)West
        // (5)Northwest, (6)Northeast, (7)Southwest, (8)Southeast.

        // Get the absolute values for relX and relY
        relY = Math.abs(relY);
        relX = Math.abs(relX);

        // Allow moving on the diagonal axes
        if (relX == relY) {
          //Check if queen is in the same place
            return (relX != 0);
        }
        // Allow moving on the horizontal and vertical axes
        if (relX == 0 || relY == 0) {
          //Check if queen is in the same place
            return (relX != 0);
        }
        // If all else fails the move is not possible
        return false;
    }

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
