package com.bbdr.chess;

public class Position {
	public static final int MIN_X = 0;
	public static final int MIN_Y = 0;
	public static final int MAX_X = 7;
	public static final int MAX_Y = 7;
	// Derived from constants.
	public static final int FACTOR_X = (1 + MAX_Y - MIN_Y);
	public static final int FACTOR_Y = 1;
	
    public int x;
    public int y;
    
    /**
     * Gets the offset for the relative position (relX, relY).
     * @param relX the relative offset-X.
     * @param relY the relative offset-Y
     * @return the offset corresponding to this relative position.
     */
    public static int getRelativeOffset(int relX, int relY) {
    	// Range of vars:
    	// -7 <= relX <= 7
    	// -7 <= relY <= 7
    	// 
    	// Sample outputs:
    	// (-7, -7) => 0 // Starts at (-7, -7)
    	// (-7, 7) => 14
    	// (-6, -7) => 15
    	// (7, 7) => 224 // Ends at (7, 7)
    	return (15 * (7 + relX)) + (7 + relY);
    }
    
    /**
     * Gets the offset for the absolute (board) position (x, y). 
     * @param x the board position X.
     * @param y the board position Y.
     * @return the offset corresponding to this absolute position.
     */
    public static int getAbsoluteOffset(int x, int y) {
    	return (FACTOR_X * x) + (FACTOR_Y * y);
    }
    
    /**
     * Gets the offset for the position's (x, y) coordinates.
     * @return the offset corresponding to this position's coordinates.
     */
    public int getAbsoluteOffset() {
    	return getAbsoluteOffset(this.x, this.y);
    }
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
