package com.bbdr.chess;

import android.view.View;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ChessBoardView extends View {
    public static final int SIZE_TILE = 35;
    public static Matrix pieceTransMatrix = new Matrix();
    public static RectF rectSource;
    public static RectF rectDestination;
    public static Paint paintHighlightContext;
    public static Paint paintHighlightTargetContext;

    public int selectionTarget = -1;

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

    public Board board = null;

    // Mock object for tile selection states.
    boolean[] tileSelectionStates = new boolean[64];

    // Initial tile states.
    int[] tilePieces = new int[64];

    public void setBoard(Board b) {
        this.board = b;
    }

    /**
     * @deprecated moved to Position.
     */
    public int getTileOffset(int x, int y) {
        return (y + (8 * x));
    }

    /**
     * @deprecated moved to Position.
     * @param offset
     * @return
     */
    public int getX(int offset) {
        return (int) Math.floor(offset / 8);
    }

    /**
     * @deprecated moved to Position.
     * @param offset
     * @return
     */
    public int getY(int offset) {
        return (offset % 8);
    }

    public boolean isSelected(int x, int y) {
        return (tileSelectionStates[getTileOffset(x, y)]);
    }

    public int getTilePiece(int x, int y) {
        return (tilePieces[getTileOffset(x, y)]);
    }

    protected int getPixels(int dp) {
        // Multiply dp by the density, which is provided
        // by the resources DisplayMetrics.
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    public boolean isSelectionTarget(int x, int y) {
        return (selectionTarget == getTileOffset(x, y));
    }

    // Moving the piece to the x and y square
    public void movePiece(int fromOffset, int toOffset) {
        int fx = getX(fromOffset), fy = getY(fromOffset);
        int tx = getX(toOffset), ty = getY(toOffset);

        if (fromOffset == toOffset) {
            // No point in moving it to the same position.
            return;
        }

        if (this.tilePieces[fromOffset] == 0) {
            // Not a valid piece ID.
            return;
        }

        this.tilePieces[toOffset] = this.tilePieces[fromOffset];
        this.tilePieces[fromOffset] = 0;
        int left = getPixels(SIZE_TILE * fx);
        int top = getPixels(SIZE_TILE * fy);
        int right = getPixels(SIZE_TILE * (fx + 1));
        int bottom = getPixels(SIZE_TILE * (fy + 1));
        // this.invalidate(10, 10, 20, 20);
        // this.invalidate(left, top, right, bottom);
        this.invalidate(getPixels(SIZE_TILE * tx), getPixels(SIZE_TILE * ty),
                getPixels(SIZE_TILE * (tx + 1)),
                getPixels(SIZE_TILE * (ty + 1)));
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (this.isInEditMode()) {
            return;
        }
        int tile;
        int pxSize = getPixels(SIZE_TILE);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                rectDestination.set(pxSize * x, pxSize * y, pxSize * (x + 1),
                        pxSize * (y + 1));
                tile = getTilePiece(x, y);
                if (isSelected(x, y) || isSelectionTarget(x, y)) {
                    canvas.drawRect(
                            rectDestination,
                            (isSelectionTarget(x, y) ? paintHighlightTargetContext
                                    : paintHighlightContext));
                }
                if (tile != 0) {
                    pieceTransMatrix.setRectToRect(rectSource, rectDestination,
                            Matrix.ScaleToFit.END);
                    canvas.drawBitmap(bitmaps[tile], pieceTransMatrix, null);
                }
            }
        }
        // super.onDraw(canvas);
    }

    @Override
    public void onMeasure(int measureWidthSpec, int measureHeightSpec) {
        // super.onMeasure(measureWidthSpec, measureHeightSpec);
        // return;
        this.setMeasuredDimension(measureWidth(measureWidthSpec),
                measureHeight(measureHeightSpec));
    }

    public int measureWidth(int spec) {
        // NOTE: this is probably not a good idea. The MeasureSpec
        // is reporting size restrictions that should not exist.
        // The documentation states that if specMode = EXACTLY,
        // the View cannot be bigger than the size provided by
        // specSize. However, in practice it doesn't seem to care,
        // i.e. it will allow us to set a size larger than specSize.
        // return getPixels(SIZE_TILE * 8);
        int result = 0;
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getPixels(SIZE_TILE * 8);
        }
        return result;
    }

    public int measureHeight(int spec) {
        // NOTE: this is probably not a good idea. The MeasureSpec
        // is reporting size restrictions that should not exist.
        // The documentation states that if specMode = EXACTLY,
        // the View cannot be bigger than the size provided by
        // specSize. However, in practice it doesn't seem to care,
        // i.e. it will allow us to set a size larger than specSize.
        // return getPixels(SIZE_TILE * 8);
        int result = 0;
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getPixels(SIZE_TILE * 8);
        }
        return result;
    }

    public int[] getInitialConfiguration(int[] pieces) {
        // Integers are pass-by-value so we can do e.g. a=b=2.
        pieces[0] = pieces[56] = ID_BROOK;
        pieces[8] = pieces[48] = ID_BKNIGHT;
        pieces[16] = pieces[40] = ID_BBISHOP;
        pieces[24] = ID_BQUEEN;
        pieces[32] = ID_BKING;
        pieces[1] = pieces[9] = pieces[17] = pieces[25] = pieces[33] = pieces[41] = pieces[49] = pieces[57] = ID_BPAWN;

        pieces[7] = pieces[63] = ID_WROOK;
        pieces[15] = pieces[55] = ID_WKNIGHT;
        pieces[23] = pieces[47] = ID_WBISHOP;
        pieces[31] = ID_WQUEEN;
        pieces[39] = ID_WKING;
        pieces[6] = pieces[14] = pieces[22] = pieces[30] = pieces[38] = pieces[46] = pieces[54] = pieces[62] = ID_WPAWN;
        return pieces;
    }

    public int[] getInitialConfiguration() {
        return getInitialConfiguration(new int[64]);
    }

    public void init() {
        Resources r = getResources();
        Bitmap sampleBitmap;
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

        // Paint context for highlighted tiles.
        paintHighlightContext = new Paint();
        paintHighlightContext.setColor(0x99ffd700);
        paintHighlightContext.setStyle(Paint.Style.FILL);

        // Paint context for selection target tile.
        paintHighlightTargetContext = new Paint();
        paintHighlightTargetContext.setColor(0xc0ffd700);
        paintHighlightTargetContext.setStyle(Paint.Style.FILL);
        // Create sample selection states.
        // Initial chess config.
        getInitialConfiguration(tilePieces);

        // Changes for testing purposes.
        // Center. Our queen is here, so also place it.
        tileSelectionStates[36] = true;
        tilePieces[36] = ID_WQUEEN;
        selectionTarget = 36;
        // Left-right.
        tileSelectionStates[4] = true;
        tileSelectionStates[12] = true;
        tileSelectionStates[20] = true;
        tileSelectionStates[28] = true;
        tileSelectionStates[44] = true;
        tileSelectionStates[52] = true;
        tileSelectionStates[60] = true;
        // Top-bottom.
        tileSelectionStates[32] = true;
        tileSelectionStates[33] = true;
        tileSelectionStates[34] = true;
        tileSelectionStates[35] = true;
        tileSelectionStates[37] = true;
        tileSelectionStates[38] = true;
        tileSelectionStates[39] = true;

        // Top left-bottom right.
        tileSelectionStates[0] = true;
        tileSelectionStates[9] = true;
        tileSelectionStates[18] = true;
        tileSelectionStates[27] = true;
        tileSelectionStates[45] = true;
        tileSelectionStates[54] = true;
        tileSelectionStates[63] = true;
        // Bottom left-top right.
        tileSelectionStates[15] = true;
        tileSelectionStates[22] = true;
        tileSelectionStates[29] = true;
        tileSelectionStates[43] = true;
        tileSelectionStates[50] = true;
        tileSelectionStates[57] = true;

        // Assumes all piece bitmaps are of the same dimensions.
        sampleBitmap = bitmaps[ID_BPAWN];

        // Create the required objects for the bitmap draw transformation
        // matrix.
        // We need to scale and translate the bitmap. The setScale() and
        // setTranslate()
        // methods overwrite the matrix state, i.e. you cannot use both
        // together.
        // Thus, we need to use the Matrix.setRectToRect, which combines scale
        // and translate.

        // First argument is the rectangle to take from the source (bitmap).
        // This will NEVER change, so we can create and initialize it once.
        // Rectangle from [0, 0] to [width, height].
        rectSource = new RectF(0F, 0F, sampleBitmap.getWidth(),
                sampleBitmap.getHeight());

        // Second argument is the rectangle to fit into the destination.
        // This will change depending on where on the board the piece is being
        // placed.
        // We will initialize it with default values, but they will need to be
        // overwritten
        // when painting is performed. Note that we create this here because we
        // don't want
        // to create new rectangle objects on every repaint.
        rectDestination = new RectF(rectSource);

        // The transformation matrix itself needs to be defined for the same
        // reason that we
        // create a destination RectF.
        pieceTransMatrix = new Matrix();

    }

    public ChessBoardView(Context context) {
        // This constructor is used by our activity.
        super(context);
        // Don't set up the initial state if we are in edit/designer mode.
        if (!this.isInEditMode()) {
            // The init method will set variables we need that are not
            // defined until Activity creation.
            init();
        }
    }

    public ChessBoardView(Context context, AttributeSet attrs) {
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
