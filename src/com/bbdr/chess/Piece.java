package com.bbdr.chess;

import java.util.HashMap;

public abstract class Piece {
    public static final int PLAYER_WHITE = 0;
    public static final int PLAYER_BLACK = 1;
    // Default case.
    public static final int PLAYER_DEFAULT = PLAYER_WHITE;
    // Number of bits for PLAYER = log2(max_player_value)
    public static final int BITS_PLAYER = (int)Math.ceil(Math.log10(PLAYER_BLACK + 1) / Math.log10(2));
    
    public static final int RANK_PAWN = 0;
    public static final int RANK_ROOK = 1;
    public static final int RANK_KNIGHT = 2;
    public static final int RANK_BISHOP = 3;
    public static final int RANK_QUEEN = 4;
    public static final int RANK_KING = 5;
    // Default case.
    public static final int RANK_DEFAULT = RANK_PAWN;
    // Number of bits for RANK = log2(max_rank_value)
    public static final int BITS_RANK = (int)Math.ceil(Math.log10(RANK_KING + 1) / Math.log10(2));
    // 6 valid ranks (Pawn, Rook, Knight, Bishop, Queen, King).
    //   2 < log2(6) <= 3 -> 3 bits
    // 2 valid ranks (White, Black).
    //   1 == log2(2) -> 1 bit.
    // Rank and player will be stored as: RRRP.
    public int getSpriteID() {
        return (getPlayer() | (getRank() << BITS_PLAYER)); 
    }
    
    public static final String[] RANK_NAMES = new String[] {
        "Pawn",
        "Rook",
        "Knight",
        "Bishop",
        "Queen",
        "King"
    };
    
    public static final String[] PLAYER_NAMES = new String[] {
        "White",
        "Black"
    };
    
    // Public fields.
    
    // Current position within the board it is contained in.
    public final Position position = new Position(-1, -1);
    
    // Piece's player name.
    public int player = PLAYER_DEFAULT;
    
    // A list of valid moves given any configuration.
    // TODO investigate SparseBooleanArray.
    public static HashMap<Integer, Boolean> validMoves;
    
    /**
     * Gets the rank ID of the piece.
     * @return the rank ID of the piece.
     */
    public abstract int getRank();
    
    /**
     * Returns true if the move to relative coordinates (relX, relY) is valid.
     * This method should be overridden for each subclass.
     * @param relX the relative coordinate X.
     * @param relY the relative coordinate Y.
     * @return true if the piece can move to relative coordinates (relX, relY).
     */
    public boolean isValidMove(int relX, int relY) {
        return true;
    }
    
    /**
     * Sets the player ID of the piece.
     * @param player the player ID to use for this piece.
     */
    public void setPlayer(int player) {
        this.player = player;
    }
    
    /**
     * Gets the player ID of the piece.
     * @return the player ID of the piece.
     */
    public int getPlayer() {
        return this.player;
    }
    
    /**
     * Moves the piece to coordinates (x, y). This method
     * does not perform any move validation. Ensure the
     * moves are valid before calling this method.
     * @param x the new x-coordinate of the piece.
     * @param y the new y-coordinate of the piece.
     */
    public void moveTo(int x, int y) {
        position.x = x;
        position.y = y;
    }
    
    /**
     * Gets the player name of the piece.
     * @return the player name of the piece.
     */
    public String getPlayerName() {
        return RANK_NAMES[this.getRank()];
    }
    
    /**
     * Gets the rank name of the piece.
     * @return the rank name of the piece.
     */
    public String getRankName() {
        return RANK_NAMES[this.getRank()];
    }
    
    /**
     * Gets the x-coordinate of the piece.
     * @return the x-coordinate of the piece.
     */
    public int getX() {
        // Notice how encapsulation allows us to define our
        // own arbitrary rules for obtaining the x-coordinate?
        return this.position.x;
    }
    
    /**
     * Gets the y-coordinate of the piece.
     * @return the y-coordinate of the piece.
     */
    public int getY() {
        // Notice how encapsulation allows us to define our
        // own arbitrary rules for obtaining the y-coordinate?
        return this.position.y;
    }
    
    @Override
    public String toString() {
        return this.getPlayerName() + " " + this.getRankName() + "@(" + position.toString() + ")";
    }
    
    public HashMap<Integer, Boolean> getValidMoves() {
        // Move list: given some coordinates (x, y) relative to
        // this piece's position on the board, is there any
        // configuration that such a move will be valid?
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        // TODO MOVE COMMENT
        // This static initializer builds a cache so that we don't
        // need to call the isValidMove(x, y) every time a piece
        // is selected etc.
        for (int x = -7; x <= 7; x++) {
            for (int y = -7; y <= 7; y++) {
                // Test if this move is valid in any configuration.
                map.put(Position.getRelativeOffset(x, y), isValidMove(x, y));
            }
        }
        return map;
    }
    
    /**
     * Constructor for Piece.
     * @param x the initial y-coordinate of the piece.
     * @param y the initial x-coordinate of the piece.
     * @param player the player ID.
     */
    public Piece(int x, int y, int player) {
        moveTo(x, y);
        setPlayer(player);
    }
    /**
     * Constructor for Piece.
     * @param x the initial x-coordinate of the piece.
     * @param y the initial y-coordinate of the piece.
     */
    public Piece(int x, int y) {
        moveTo(x, y);
    }
    
    /**
     * Constructor for Piece.
     */
    public Piece() {
        
    }
}