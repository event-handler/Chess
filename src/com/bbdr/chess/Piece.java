package com.bbdr.chess;

import java.util.HashMap;

public abstract class Piece {
    /** Indicates that the Piece belongs to no player. This should
     * never be used for pieces, but can be used to compare Pieces
     * without considering player.
     */
    public static final int PLAYER_NONE = 0;
    
    /** Indicates that the Piece belongs to the first player (White). */
    public static final int PLAYER_WHITE = 1;
    
    /** Indicates that the Piece belongs to the second player (Black). */
    public static final int PLAYER_BLACK = 2;
    
    /** Maximum allowed Player value. */
    public static final int PLAYER_MAXVAL = PLAYER_BLACK;
    
    /** The default player. */
    public static final int PLAYER_DEFAULT = PLAYER_WHITE;
    
    /** Indicates that the Piece is of no rank. This hsould never
     * be used for pieces, but can be used to compare Pieces
     * without considering their rank.
     */
    public static final int RANK_NONE = 0;
    
    /** Indicates that a Piece is of rank Pawn. */
    public static final int RANK_PAWN = 1;
    
    /** Indicates that a Piece is of rank Rook. */
    public static final int RANK_ROOK = 2;
    
    /** Indicates that a Piece is of rank Knight. */
    public static final int RANK_KNIGHT = 3;
    
    /** Indicates that a Piece is of rank Bishop. */
    public static final int RANK_BISHOP = 4;
    
    /** Indicates that a Piece is of rank Queen. */
    public static final int RANK_QUEEN = 5;
    
    /** Indicates that a Piece is of rank King. */
    public static final int RANK_KING = 6;
    
    /** Maximum allowed Rank value. */
    public static final int RANK_MAXVAL = RANK_KING;
    
    /** The default rank. */
    public static final int RANK_DEFAULT = RANK_PAWN;
    
    /** The names of ranks. Each index corresponds to their Rank number. */
    public static final String[] RANK_NAMES = new String[] {
        "N/A", "Pawn", "Rook", "Knight", "Bishop", "Queen", "King"
    };
    
    /** The names of players. Each index corresponds to their Player number. */
    public static final String[] PLAYER_NAMES = new String[] {
        "N/A", "White", "Black"
    };
    
    /** The Piece's current position within the board. */
    public final Position position = new Position(-1, -1);
    
    /** The Piece's player number. */
    public int player = PLAYER_DEFAULT;
    
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
     * @param useCache whether or not the cache should be used.
     * @return true if the piece can move to relative coordinates (relX, relY).
     */
    public boolean isValidMove(int relX, int relY, boolean useCache) {
        return true;
    }
    
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
    
    /**
     * Gets the fingerprint of the input rank and player.
     * @param rank the piece's rank.
     * @param player the piece's player.
     * @return the fingerprint of the input rank and player.
     */
    public static int getFingerprint(int rank, int player) {
        return (7 * (player % 3) + (rank % 7));
    }
    
    /**
     * Gets the piece's fingerprint. This can be used to test if
     * a piece matches a certain rank and/or player.
     * @return the fingerprint of the Piece.
     */
    public int getFingerprint() {
        return getFingerprint(getRank(), getPlayer());
    }
    
    /**
     * Checks if the Piece matches an input fingerprint.
     * @param fingerprint the fingerprint to test.
     * @return true if the Piece matches the fingerprint.
     */
    public boolean matches(int fingerprint) {
        int p = (int)(fingerprint / 7);
        int pt = (int)(getFingerprint() / 7);
        int r = (int)(fingerprint % 7);
        int rt = (int)(getFingerprint() % 7);
        return (p == 0 || p == pt) && (r == 0 || r == rt);
    }
    
    @Override
    public String toString() {
        return this.getPlayerName() + " " + this.getRankName() + "@(" + position.toString() + ")";
    }
    
    /**
     * Generates a cache of all valid moves for this Piece type.
     */
    public static void generateValidMoves(HashMap<Integer, Boolean> map, Piece p) {
        for (int x = -7; x <= 7; x++) {
            for (int y = -7; y <= 7; y++) {
                // Test if this move is valid in any configuration.
                map.put(Position.getRelativeOffset(x, y), p.isValidMove(x, y, false));
            }
        }
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