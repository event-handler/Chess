package com.bbdr.chess;

import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

public class ChessPieceView extends View {
    
    /**
    * This class creates a custom View that can be placed
    * on the layout to implement custom drawing behavior. 
    * In this case, the View will draw a specific bitmap
    * (Queen, King, etc.) depending on the View's state.
    */
   
    public static final int SIZE_TILE = 35;
    protected static Bitmap[] bitmaps = new Bitmap[13];
    protected static final int ID_BOARD = 0;
    protected static final int ID_WPAWN = 1;
    protected static final int ID_WROOK = 2;
    protected static final int ID_WKNIGHT = 3;
    protected static final int ID_WBISHOP = 4;
    protected static final int ID_WQUEEN = 5;
    protected static final int ID_WKING = 6;
    protected static final int ID_BPAWN = 7;
    protected static final int ID_BROOK = 8;
    protected static final int ID_BKNIGHT = 9;
    protected static final int ID_BBISHOP = 10;
    protected static final int ID_BQUEEN = 11;
    protected static final int ID_BKING = 12;

    Matrix pieceTransMatrix = new Matrix();
    RectF rectSource = null;
    RectF rectDestination = null;

    // the view for a chess piece
    public int pieceID = 1;

    @Override
    public void onDraw(Canvas canvas) {

        if (this.isInEditMode()) {
            return;
        }
        pieceTransMatrix.setRectToRect(rectSource, rectDestination,
                Matrix.ScaleToFit.END);
        canvas.drawBitmap(bitmaps[pieceID], pieceTransMatrix, null);
    }

    public void init() {
        Resources r = getResources();
        bitmaps[ID_BOARD] = BitmapFactory.decodeResource(r,
                R.drawable.tempchessboard);
        bitmaps[ID_BPAWN] = BitmapFactory.decodeResource(r,
                R.drawable.pawn_black);
        bitmaps[ID_BROOK] = BitmapFactory.decodeResource(r,
                R.drawable.castle_black);
        bitmaps[ID_BKNIGHT] = BitmapFactory.decodeResource(r,
                R.drawable.knight_black);
        bitmaps[ID_BBISHOP] = BitmapFactory.decodeResource(r,
                R.drawable.bishop_black);
        bitmaps[ID_BQUEEN] = BitmapFactory.decodeResource(r,
                R.drawable.queen_black);
        bitmaps[ID_BKING] = BitmapFactory.decodeResource(r,
                R.drawable.king_black);
        bitmaps[ID_WPAWN] = BitmapFactory.decodeResource(r,
                R.drawable.pawn_white);
        bitmaps[ID_WROOK] = BitmapFactory.decodeResource(r,
                R.drawable.castle_white);
        bitmaps[ID_WKNIGHT] = BitmapFactory.decodeResource(r,
                R.drawable.knight_white);
        bitmaps[ID_WBISHOP] = BitmapFactory.decodeResource(r,
                R.drawable.bishop_white);
        bitmaps[ID_WQUEEN] = BitmapFactory.decodeResource(r,
                R.drawable.queen_white);
        bitmaps[ID_WKING] = BitmapFactory.decodeResource(r,
                R.drawable.king_white);

        rectSource = new RectF(0F, 0F, bitmaps[1].getWidth(),
                bitmaps[1].getHeight());
        rectDestination = new RectF(0F, 0F, bitmaps[1].getWidth(),
                bitmaps[1].getHeight());
    }

    protected int getPixels(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);

    }

    public void onMeasure(int measureWidthSpec, int measureHeightSpec) {
        // sets the measurements of the view
        this.setMeasuredDimension(measureWidth(measureWidthSpec),
                measureHeight(measureHeightSpec));

    }

    // make the piece size match tile size
    public int measureWidth(int spec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getPixels(SIZE_TILE);
        }
        return result;
    }

    // make the piece size match tile size
    public int measureHeight(int spec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getPixels(SIZE_TILE);
        }
        return result;
    }

    public ChessPieceView(Context context) {
        // This constructor is used by our activity.
        super(context);
        // Don't set up the initial state if we are in edit/designer mode.
        if (!this.isInEditMode()) {
            // The init method will set variables we need that are not
            // defined until Activity creation.
            init();
        }
    }

    // AttributeSet of properties specified in the xml resource file. This is
    // called to intialise a view from a a layout file
    public ChessPieceView(Context context, AttributeSet attrs) {
        // This constructor is used by the UI designer.
        super(context, attrs);
        // Don't set up the initial state if we are in edit/designer mode.
        if (!this.isInEditMode()) {
            // The init method will set variables we need that are not
            // defined until Activity creation.
            init();
        }
    }
}
