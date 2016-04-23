package com.bbdr.chess;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;


public class ChessSprite {
    /**
     This class is designed to be a static
     class for creating an array of bitmaps 
     and intialising them.
     */
    
    public static Bitmap[] bitmaps = new Bitmap[Piece.getFingerprint(Piece.RANK_MAXVAL, Piece.PLAYER_MAXVAL) + 1];
    
    // RectF rectSource = null;
    //  RectF rectDestination = null;
    
    public static void initBitmaps(Resources r) {

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
        
        //rectSource = new RectF(0F, 0F, bitmaps[1].getWidth(),bitmaps[1].getHeight());
        //rectDestination = new RectF(0F, 0F, bitmaps[1].getWidth(),bitmaps[1].getHeight());
        
    
      //Bitmap sBitmap = null;
      //sBitmap = bitmaps[Piece.getFingerprint(Piece.RANK_PAWN, Piece.PLAYER_BLACK)];
           
        
    }
}
