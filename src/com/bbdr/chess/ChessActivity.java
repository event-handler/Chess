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
    private RelativeLayout.LayoutParams refLayoutParams;

    // View
    private ChessBoardView boardView = null;

    // Model
    private Board board = null;

    // public final int SIZE_TILE = getPixels(35);
    public final int SIZE_TILE = 35;

    protected int getPixels(int dp) {
        // Multiply dp by the density, which is provided
        // by the resources DisplayMetrics.
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    protected int getDp(int px) {
        // Divide px by the density, which is provided
        // by the resources DisplayMetrics.
        return (int) (px / getResources().getDisplayMetrics().density);
    }

    protected void clearCurrentSelection() {
        if (this.board != null) {
            this.board.selected = null;
        }
    }

    protected void setSelected(Piece piece) {
        if (this.board != null) {
            this.board.selected = piece;
            if (this.boardView != null) {
                this.boardView.updateValidMoves();
            }
        }
    }

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
        board.add(new Bishop(2, 7, Piece.PLAYER_WHITE));
        board.add(new Queen(3, 7, Piece.PLAYER_WHITE));
        board.add(new King(4, 7, Piece.PLAYER_WHITE));
        board.add(new Bishop(5, 7, Piece.PLAYER_WHITE));
        board.add(new Knight(6, 7, Piece.PLAYER_WHITE));
        board.add(new Rook(7, 7, Piece.PLAYER_WHITE));
    }

    protected void init() {
        refLayoutParams = new RelativeLayout.LayoutParams(getPixels(SIZE_TILE),
                getPixels(SIZE_TILE));

        // Test Pawn.
        PieceMovementTest.testValidMovements(new Pawn());
        // Test King.
        PieceMovementTest.testValidMovements(new King());
        // Test Rook.
        PieceMovementTest.testValidMovements(new Rook());
        // Test Bishop
        PieceMovementTest.testValidMovements(new Bishop());
        // Test Queen
        PieceMovementTest.testValidMovements(new Queen());
        // Test Knight
        PieceMovementTest.testValidMovements(new Queen());

        // Set the board view.
        boardView = (ChessBoardView) findViewById(R.id.chessboardView);
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
                // If no Piece was selected, clear the current selection.
                if (piece == null) {
                    // It's possible that nothing is selected already.
                    if (board.selected == null) {
                        return true;
                    }
                    // We're clearing the current selection.
                    clearCurrentSelection();
                    return true;
                }

                // A Piece has been selected.
                // If the Piece was selected again, deselect it.
                if (board.selected == piece) {
                    clearCurrentSelection();
                    return true;
                }
                clearCurrentSelection();
                setSelected(piece);
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
