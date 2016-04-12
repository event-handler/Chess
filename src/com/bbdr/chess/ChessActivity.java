package com.bbdr.chess;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.util.Log;

public class ChessActivity extends Activity {
    
    /** Contains the View for the chess board. */
    private ChessBoardView boardView = null;

    /** Contains the Model for the chess board. */
    private Board board = null;

    /** The size of a tile, in density pixels (dp). */
    public final int SIZE_TILE = 35;

    /**
     * Gets the pixel size for the specified density pixels.
     * @param dp the density pixels to convert to pixels.
     * @return the pixels represented by the density pixels.
     */
    protected int getPixels(int dp) {
        // Multiply dp by the density, which is provided
        // by the resources DisplayMetrics.
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    /**
     * Gets the density pixels for the specified pixels.
     * @param px the pixels to convert to density pixels.
     * @return the density pixels represented by the pixels.
     */
    protected int getDp(int px) {
        // Divide px by the density, which is provided
        // by the resources DisplayMetrics.
        return (int) (px / getResources().getDisplayMetrics().density);
    }

    /**
     * Clears the current selected tile.
     */
    protected void clearCurrentSelection() {
        if (this.board != null) {
            this.board.selected = null;
        }
    }

    /**
     * Selects a piece, to activate move suggestion and allow piece movement.
     * @param piece the piece to select.
     */
    protected void setSelected(Piece piece) {
        if (this.board != null) {
            this.board.selected = piece;
            if (this.boardView != null) {
                this.boardView.updateValidMoves();
            }
        }
    }

    /**
     * Creates the initial board layout.
     */
    protected void startGame() {
        // Create the board.
        board = new Board();
        // Row 1.
        board.add(new Rook(0, 0, Piece.PLAYER_BLACK));
        board.add(new Knight(1, 0, Piece.PLAYER_BLACK));
        board.add(new Bishop(2, 0, Piece.PLAYER_BLACK));
        board.add(new Queen(3, 0, Piece.PLAYER_BLACK));
        board.add(new King(4, 0, Piece.PLAYER_BLACK));
        board.add(new Bishop(5, 0, Piece.PLAYER_BLACK));
        board.add(new Knight(6, 0, Piece.PLAYER_BLACK));
        board.add(new Rook(7, 0, Piece.PLAYER_BLACK));
        // Row 2.
        board.add(new Pawn(0, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(1, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(2, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(3, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(4, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(5, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(6, 1, Piece.PLAYER_BLACK));
        board.add(new Pawn(7, 1, Piece.PLAYER_BLACK));
        // Row 3.
        board.add(new Pawn(0, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(1, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(2, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(3, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(4, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(5, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(6, 6, Piece.PLAYER_WHITE));
        board.add(new Pawn(7, 6, Piece.PLAYER_WHITE));
        // Row 4.
        board.add(new Rook(0, 7, Piece.PLAYER_WHITE));
        board.add(new Knight(1, 7, Piece.PLAYER_WHITE));
        //board.add(new Knight(5, 3, Piece.PLAYER_WHITE));
        board.add(new Bishop(2, 7, Piece.PLAYER_WHITE));
        board.add(new Queen(3, 7, Piece.PLAYER_WHITE));
        board.add(new King(4, 7, Piece.PLAYER_WHITE));
        board.add(new Bishop(5, 7, Piece.PLAYER_WHITE));
        board.add(new Knight(6, 7, Piece.PLAYER_WHITE));
        board.add(new Rook(7, 7, Piece.PLAYER_WHITE));
    }

    /**
     * Initializes the Activity.
     */
    protected void init() {
        // Test Pawn movement.
        PieceMovementTest.testValidMovements(new Pawn());
        // Test King movement.
        PieceMovementTest.testValidMovements(new King());
        // Test Rook movement.
        PieceMovementTest.testValidMovements(new Rook());
        // Test Bishop movement.
        PieceMovementTest.testValidMovements(new Bishop());
        // Test Queen movement.
        PieceMovementTest.testValidMovements(new Queen());
        // Test Knight movement.
        PieceMovementTest.testValidMovements(new Queen());

        // Set the board view.
        boardView = (ChessBoardView)findViewById(R.id.chessboardView);
        boardView.setBoard(board);
        
        // On touch listener for Relative layouts
        boardView.setOnTouchListener(new RelativeLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent m) {
                int x = (int) m.getX();
                int y = (int) m.getY();
                int posX = (int) (getDp(x) / SIZE_TILE);
                int posY = (int) (getDp(y) / SIZE_TILE);
                Piece piece;
                // Make sure the x and y are within the board.
                if (!(0 <= posX && posX < 8)) {
                    return true;
                }
                if (!(0 <= posY && posY < 8)) {
                    return true;
                }

                // Make sure we aren't selecting the same piece.
                piece = board.get(posX, posY);
                if (board.selected != null) {
                    // A piece is already selected.
                    // If they selected the same piece, deselect it.
                    if (board.selected == piece) {
                        clearCurrentSelection();
                        return true;
                    }
                    // If we can move to this position, move.
                    if (board.pieceCanMoveTo((Moveable)board.selected, posX, posY)) {
                        board.move(board.selected, posX, posY);
                        boardView.update();
                        clearCurrentSelection();
                        return true;
                    }
                    // Clear the current selection if they did not move or deselect.
                    clearCurrentSelection();
                }
                // If the user touched a piece, select it.
                if (piece != null) {
                    setSelected(piece);
                }
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        // Initialize the activity's vars.
        startGame();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chess, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
