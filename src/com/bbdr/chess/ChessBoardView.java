package com.bbdr.chess;

import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.graphics.Rect;
import android.util.Log;

public class ChessBoardView extends ViewGroup {
    public static final int SIZE_TILE = 35;
    public static RectF rectSource = new RectF();
    public static RectF rectDestination = new RectF();
    public static Paint paintHighlightContext;
    public static Paint paintHighlightTargetContext;

    public int selectionTarget = -1;

    protected static Bitmap[] bitmaps = new Bitmap[13];
    
    public Board board = null;

    /** The amount of space used by children in the left gutter. */
    private int mLeftWidth;

    /** The amount of space used by children in the right gutter. */
    private int mRightWidth;

    /** These are used for computing child frames based on their gravity. */
    private final Rect mTmpContainerRect = new Rect();
    private final Rect mTmpChildRect = new Rect();
    
    // Mock object for tile selection states.
    boolean[] tileSelectionStates = new boolean[64];

    // Initial tile states.
    int[] tilePieces = new int[64];

    public void updateValidMoves() {
        Board b = this.board;
        Moveable p = (Moveable)b.selected;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.tileSelectionStates[Position.getAbsoluteOffset(x, y)] = (p != null && b.pieceCanMoveTo(p, x, y));
                Log.d("chessfagstates", "" + x + ", " + y + " : " + (this.tileSelectionStates[Position.getAbsoluteOffset(x, y)]));
            }
        }
        this.invalidate();
    }
    
    public void setBoard(Board b) {
        this.board = b;
        // Remove all views and add the board's views.
        this.removeAllViews();
        if (b == null) {
            return;
        }
        for (int i = 0; i < board.pieces.length; i++) {
            this.addView(createPieceViewForPiece(board.pieces[i]));
        }
        // TODO remove
        this.invalidate();
    }
    
    public boolean isSelected(int x, int y) {
        return (tileSelectionStates[Position.getAbsoluteOffset(x, y)]);
    }

    protected int getPixels(int dp) {
        // Multiply dp by the density, which is provided
        // by the resources DisplayMetrics.
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    public boolean isSelectionTarget(int x, int y) {
        if (this.board == null || this.board.selected == null) {
            return false;
        }
        return (this.board.selected == this.board.get(x, y));
    }

    // TODO not used anymore, remove.
    @Override
    public void onDraw(Canvas canvas) {
        if (this.isInEditMode()) {
            return;
        }
        if (this.board == null || this.board.selected == null) {
            // We have no board, or nothing is selected.
            // There is nothing to do.
            return;
        }
        int pxSize = getPixels(SIZE_TILE);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                rectDestination.set(pxSize * x, pxSize * y, pxSize * (x + 1),
                        pxSize * (y + 1));
                if (isSelected(x, y) || isSelectionTarget(x, y)) {
                    canvas.drawRect(rectDestination,
                            (isSelectionTarget(x, y) ? paintHighlightTargetContext
                                    : paintHighlightContext));
                }
            }
        }
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

    public void init() {
        // Paint context for highlighted tiles.
        paintHighlightContext = new Paint();
        paintHighlightContext.setColor(0x99ffd700);
        paintHighlightContext.setStyle(Paint.Style.FILL);

        // Paint context for selection target tile.
        paintHighlightTargetContext = new Paint();
        paintHighlightTargetContext.setColor(0xc0ffd700);
        paintHighlightTargetContext.setStyle(Paint.Style.FILL);
    }
    
    protected ChessPieceView createPieceViewForPiece(Piece p) {
        ChessPieceView v = new ChessPieceView(this.getContext());
        // Declares the sprite to draw.
        v.setPiece(p);
        // Set the layout parameters to set the width and height of this view. 
        v.setLayoutParams(this.generateLayoutParams(new ViewGroup.LayoutParams(getPixels(SIZE_TILE), getPixels(SIZE_TILE))));
        // Need to measure the piece view or it won't register that it has a width/height.
        v.measure(0, 0);
        
        // Set the coordinates. (0-7) * size_of_tile
        v.setX((int)(p.getX() * getPixels(SIZE_TILE)));
        v.setY((int)(p.getY() * getPixels(SIZE_TILE)));
        return v;
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();

        // This is the middle region inside of the gutter.
        final int middleLeft = leftPos + mLeftWidth;
        final int middleRight = rightPos - mRightWidth;

        // These are the top and bottom edges in which we are performing layout.
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                // Compute the frame in which we are placing this child.
                if (lp.position == LayoutParams.POSITION_LEFT) {
                    mTmpContainerRect.left = leftPos + lp.leftMargin;
                    mTmpContainerRect.right = leftPos + width + lp.rightMargin;
                    leftPos = mTmpContainerRect.right;
                } else if (lp.position == LayoutParams.POSITION_RIGHT) {
                    mTmpContainerRect.right = rightPos - lp.rightMargin;
                    mTmpContainerRect.left = rightPos - width - lp.leftMargin;
                    rightPos = mTmpContainerRect.left;
                } else {
                    mTmpContainerRect.left = middleLeft + lp.leftMargin;
                    mTmpContainerRect.right = middleRight - lp.rightMargin;
                }
                mTmpContainerRect.top = parentTop + lp.topMargin;
                mTmpContainerRect.bottom = parentBottom - lp.bottomMargin;

                // Use the child's gravity and size to determine its final
                // frame within its container.
                Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect);

                // Place the child.
                child.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ChessBoardView.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }
    
    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;

        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;

        public int position = POSITION_MIDDLE;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            // Pull the layout param values from the layout XML during
            // inflation.  This is not needed if you don't care about
            // changing the layout behavior in XML.
            //TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP);
            //gravity = a.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity);
            //position = a.getInt(R.styleable.CustomLayoutLP_layout_position, position);
            //a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
    
    public ChessBoardView(Context context) {
        // This constructor is used by our activity.
        super(context);
        // Don't set up the initial state if we are in edit/designer mode.
        if (!this.isInEditMode()) {
            // ViewGroups are not drawn by default, so we need to explicitly tell it to.
            this.setWillNotDraw(false);
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
            // ViewGroups are not drawn by default, so we need to explicitly tell it to.
            this.setWillNotDraw(false);
            // The init method will set variables we need that are not
            // defined until Activity creation.
            init();
        }
    }

}
