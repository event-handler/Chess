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
    protected static Bitmap[] bitmaps = new Bitmap[Piece.getFingerprint(Piece.RANK_MAXVAL, Piece.PLAYER_MAXVAL) + 1];
    
    protected static final Matrix pieceTransMatrix = new Matrix();
    protected static final RectF rectSource = new RectF();
    protected static final RectF rectDestination = new RectF();

    // the view for a chess piece
    protected int pieceID = -1;

    @Override
    public void onDraw(Canvas canvas) {

        if (this.isInEditMode()) {
            return;
        }
        // We don't want to do anything if the piece ID does
        // not map to a Bitmap. Otherwise, uh, exception.
        if (pieceID == -1 || bitmaps.length < pieceID || bitmaps[pieceID] == null) {
            // This should NOT have happened.
            // It's probably Bailey's fault.
            // Blame him if you cannot figure it out.
            // Oh. Can't throw exceptions in this method.
            //throw new BaileysFaultException("You done fucked up");
            return;
        }
        
        // Set the rectangle that we are drawing to.
        rectDestination.set(0F, 0F, this.getMeasuredWidth(), this.getMeasuredHeight());
        
        // Construct the matrix that transforms the Bitmap into our tile area.
        pieceTransMatrix.setRectToRect(rectSource, rectDestination,
                Matrix.ScaleToFit.END);
        
        // Draw the bitmap to the canvas according to the transformation matrix.
        canvas.drawBitmap(bitmaps[pieceID], pieceTransMatrix, null);
    }

    public void setSpriteID(int id) {
        // Check that the piece ID is even different.
        if (this.pieceID != id) {
            // It's different. Overwrite the ID and mark this view
            // for re-rendering.
            this.pieceID = id;
            this.invalidate();
        }
        // If it's not different, then we don't need to change anything.
    }
    
    public void setPiece(Piece piece) {
        this.setSpriteID(piece.getFingerprint());
    }
    
    public void init() {
        Resources r = getResources();
        Bitmap sBitmap = null;
        // Store the bitmaps for later use.
        // TODO move this to another class. This is inefficient,
        // because it creates the array for each piece view.
        bitmaps[Piece.getFingerprint(Piece.RANK_PAWN, Piece.PLAYER_BLACK)] =
                BitmapFactory.decodeResource(r, R.drawable.pawn_black);
        bitmaps[Piece.getFingerprint(Piece.RANK_ROOK, Piece.PLAYER_BLACK)] =
                BitmapFactory.decodeResource(r, R.drawable.castle_black);
        bitmaps[Piece.getFingerprint(Piece.RANK_KNIGHT, Piece.PLAYER_BLACK)] =
                BitmapFactory.decodeResource(r, R.drawable.knight_black);
        bitmaps[Piece.getFingerprint(Piece.RANK_BISHOP, Piece.PLAYER_BLACK)] =
                BitmapFactory.decodeResource(r, R.drawable.bishop_black);
        bitmaps[Piece.getFingerprint(Piece.RANK_QUEEN, Piece.PLAYER_BLACK)] =
                BitmapFactory.decodeResource(r, R.drawable.queen_black);
        bitmaps[Piece.getFingerprint(Piece.RANK_KING, Piece.PLAYER_BLACK)] =
                BitmapFactory.decodeResource(r, R.drawable.king_black);
        bitmaps[Piece.getFingerprint(Piece.RANK_PAWN, Piece.PLAYER_WHITE)] =
                BitmapFactory.decodeResource(r, R.drawable.pawn_white);
        bitmaps[Piece.getFingerprint(Piece.RANK_ROOK, Piece.PLAYER_WHITE)] =
                BitmapFactory.decodeResource(r, R.drawable.castle_white);
        bitmaps[Piece.getFingerprint(Piece.RANK_KNIGHT, Piece.PLAYER_WHITE)] =
                BitmapFactory.decodeResource(r, R.drawable.knight_white);
        bitmaps[Piece.getFingerprint(Piece.RANK_BISHOP, Piece.PLAYER_WHITE)] =
                BitmapFactory.decodeResource(r, R.drawable.bishop_white);
        bitmaps[Piece.getFingerprint(Piece.RANK_QUEEN, Piece.PLAYER_WHITE)] =
                BitmapFactory.decodeResource(r, R.drawable.queen_white);
        bitmaps[Piece.getFingerprint(Piece.RANK_KING, Piece.PLAYER_WHITE)] =
                BitmapFactory.decodeResource(r, R.drawable.king_white);
        sBitmap = bitmaps[Piece.getFingerprint(Piece.RANK_PAWN, Piece.PLAYER_BLACK)];
        // The source rectangle does not change because we have
        // bitmaps of the same height and width.
        rectSource.set(0.0F, 0.0F, sBitmap.getWidth(), sBitmap.getHeight());
        // We need to redefine the destination rectangle's bounds
        // every redraw, but we should create the object now.
        rectDestination.set(0.0F, 0.0F, sBitmap.getWidth(), sBitmap.getHeight());
    }

    protected int getPixels(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    @Override
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
