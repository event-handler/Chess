package com.bbdr.chess;

import java.lang.Exception;

public class Board {
    
    /**
     * The board's tile array. We don't need to store any information
     * for each tile besides the Piece it contains. Therefore, it is
     * an array of Pieces.
     */
    public Piece[] tiles = new Piece[8 * 8];
    public Piece[] pieces = new Piece[4 * 8];
    
    /**
     * Indexer for pieces.
     */
    protected int pieceID = 0;
    
    /**
     * Which piece is selected.
     */
    public Piece selected = null;
    
    /**
     * Adds a piece to the board. The board will use the Piece's
     * information such as x and y coordinates to decide where it goes.
     * @param p the piece to place on the board.
     */
    public void add(Piece p) {
        int offset = p.position.getAbsoluteOffset();
        if (tiles[offset] != null) {
            // There is a piece at this offset.
            // What should we do?
        }
        tiles[offset] = p;
        pieces[pieceID++] = p;
    }
    
    /**
     * Finds the index of the piece, used as the piece's ID for this board.
     * @param p the Piece to search for the ID of.
     * @return the ID of the piece, unique for this board.
     */
    public int findPieceID(Piece p) {
        for (int i = 0; i < this.pieces.length; i++) {
            if (this.pieces[i] == p) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Replaces a Piece with another Piece.
     * @param oldPiece the old piece.
     * @param newPiece the new piece.
     */
    public void replace(Piece oldPiece, Piece newPiece) throws Exception {
        int index = findPieceID(oldPiece);
        if (index == -1) {
            throw new Exception("Could not find Piece for replacement.");
        }
        this.pieces[index] = newPiece;
    }
    
    /**
     * Removes a piece from the board.
     * @param p the piece to remove from the board.
     */
    public void remove(Piece p) {
        int offset = p.position.getAbsoluteOffset();
        if (tiles[offset] == p) {
            // Useless sanity-check.
            tiles[offset] = null;
        }
    }
    
    /**
     * Moves the piece to the coordinates (x, y).
     * @param piece the piece to move
     * @param x the x-coordinate to move the piece to.
     * @param y the y-coordinate to move the piece to.
     */
    public void move(Piece piece, int x, int y) {
        int index = this.findPieceID(piece);
        if (index == -1) {
            // The piece does not exist. We're not moving something
            // that isn't on the board. We should probably check
            // that the inputs were valid.
            return;
        }
        piece.position.x = x;
        piece.position.y = y;
    }
    
    /**
     * Selects the piece at (x, y).
     * @param x the x-coordinate of the tile to select.
     * @param y the y-coordinate of the tile to select.
     */
    public void selectAt(int x, int y) {
        selected = this.get(x, y);
    }
    
    /**
     * Gets the piece at (x, y).
     * @param x the x-coordinate of the Piece to get.
     * @param y the y-coordinate of the Piece to get.
     * @return the Piece at (x, y) or null if there aren't any.
     */
    public Piece get(int x, int y) {
        return tiles[Position.getAbsoluteOffset(x,  y)];
    }
    
    /**
     * Checks if (x, y) is occupied by a piece.
     * @param x the x-coordinate of the tile to check.
     * @param y the y-coordinate of the tile to check.
     * @return true if there is a piece at (x, y); false otherwise.
     */
    public boolean isOccupied(int x, int y) {
        return (get(x, y) != null);
    }
    
    /**
     * Checks if (x, y) is occupied by a piece matching a certain fingerprint.
     * @param x the x-coordinate to check.
     * @param y the y-coordinate to check.
     * @param fingerprint the fingerprint to test against.
     * @return true if the position (x, y) is occupied by a piece matching the fingerprint.
     */
    public boolean isOccupiedBy(int x, int y, int fingerprint) {
        Piece p = get(x, y);
        return p != null && p.matches(fingerprint);
    }
    
    /**
     * Determines if a piece can move to (x, y).
     * @param piece the Piece to move.
     * @param x the x-coordinate of the tile to move to.
     * @param y the y-coordinate of the tile to move to.
     * @return true if the piece can move to (x, y).
     */
    public boolean pieceCanMoveTo(Moveable piece, int x, int y) {
        // We've been given absolute X and Y coordinates.
        // We need to calculate their relative positions.
        int relativeX = (x - piece.getX());
        int relativeY = (y - piece.getY());
        
        // Test if the piece can move to this position
        // in any board configuration.
        if (!piece.isValidMove(relativeX, relativeY)) {
            // This isn't a valid move either way.
            return false;
        }
        // This move is valid in some configuration, but we need to check for
        // our current configuration.
        if (!piece.canMoveTo(relativeX, relativeY, this)) {
            return false;
        }
        // Check that the tile is not occupied by the player's piece. 
        if (this.isOccupiedBy(x, y, Piece.getFingerprint(Piece.PLAYER_NONE, piece.getPlayer()))) {
            return false;
        }
        return true;
    }
}