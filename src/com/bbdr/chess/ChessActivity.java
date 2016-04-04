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
    
    private ChessBoardView boardView = null;
    
    //public final int SIZE_TILE = getPixels(35);
    public final int SIZE_TILE = 35;
    
    protected int getPixels(int dp) {
        // Multiply dp by the density, which is provided
        // by the resources DisplayMetrics.
        return (int)(getResources().getDisplayMetrics().density * dp);
    }
    protected int getDp(int px) {
        // Divide px by the density, which is provided
        // by the resources DisplayMetrics.
        return (int)(px / getResources().getDisplayMetrics().density);
    }
    
    protected void init() {
        refLayoutParams = new RelativeLayout.LayoutParams(getPixels(SIZE_TILE), getPixels(SIZE_TILE));
        
        King k = new King();
        for (int x = -7; x <= 7; x++) {
            for (int y = -7; y <= 7; y++) {
                if (k.isValidMove(x, y)) {
                    Log.d("chessfag", "(" + x + ", " + y + ") : valid");
                }
            }
        }
        // Create the board view dynamically.
        //RelativeLayout relLayout = (RelativeLayout)findViewById(R.id.chessboardLayout);
        
        // Set the board view.
        boardView = (ChessBoardView)findViewById(R.id.chessboardView);
        //boardView = new ChessBoardView(this);
        //relLayout.addView(boardView, new RelativeLayout.LayoutParams(refLayoutParams));
        
        //On touch listener for Relative layouts
        boardView.setOnTouchListener(new RelativeLayout.OnTouchListener() {
               public boolean onTouch(View v, MotionEvent m) {
                   int prevSelectionTarget = boardView.selectionTarget;
                   int x = (int)m.getX();
                   int y = (int)m.getY();
                   int posX = (int)(getDp(x) / SIZE_TILE);
                   int posY = (int)(getDp(y) / SIZE_TILE);
                   if (!(0 <= posX && posX < 8)) {
                       return true;
                   }
                   if (!(0 <= posY && posY < 8)) {
                       return true;
                   }
                   if (prevSelectionTarget == boardView.getTileOffset(posX, posY)) {
                       return true;
                   }
                   boardView.selectionTarget = boardView.getTileOffset(posX, posY);
                   
                   
                   if (prevSelectionTarget != -1) {
                    boardView.movePiece(prevSelectionTarget, boardView.selectionTarget);
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
