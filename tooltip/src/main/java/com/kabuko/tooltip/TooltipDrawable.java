package com.kabuko.tooltip;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import com.kabuko.tooltip.Tooltip.Direction;

/**
 * Tooltip.
 *
 * Created by franciskam on 1/29/15.
 */
public class TooltipDrawable extends Drawable {
    private int mArrowSize;
    private int mCornerRadius;
    private int mArrowGravity;
    private Tooltip.Direction mDirection;
    private int mArrowWidth;
    private int mArrowHeight;
    private int mHalfArrowHeight;
    private int mHalfArrowWidth;
    private Path mArrowPath;
    private Paint mPaint;
    private Rect mBounds;
    private int mArrowDx;
    private int mArrowDy;

    /**
     * Creates a new Tooltip
     *
     *  2 * corner radius + arrow size must be less than anchor width.
     *
     * @param color
     * @param cornerRadius
     * @param arrowSize
     */
    public TooltipDrawable(int color, int cornerRadius, int arrowSize) {
        this(color, cornerRadius, arrowSize, Direction.Below);
    }

    public TooltipDrawable(int color, int cornerRadius, int arrowSize, Direction direction) {
        this(color, cornerRadius, arrowSize, direction, Gravity.CENTER_HORIZONTAL);
    }

    public TooltipDrawable(int color, int cornerRadius, int arrowSize, Direction direction, int arrowGravity) {
        mCornerRadius = cornerRadius;

        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);

        mDirection = direction;
        mArrowGravity = arrowGravity;
        mArrowSize = arrowSize;

        updateArrowDimens();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mBounds = new Rect(bounds);

        int x1;
        int y1;
        int x2;
        int y2;
        int x3;
        int y3;

        int verticalGravity = Gravity.VERTICAL_GRAVITY_MASK & mArrowGravity;
        int horizontalGravity = Gravity.HORIZONTAL_GRAVITY_MASK & mArrowGravity;
        int y;
        int x;

        switch(mDirection) {
            case ToRight:
                // Tooltip to right, arrow on left
                mBounds.left += mArrowWidth;

                switch(verticalGravity) {
                    case Gravity.TOP:
                        y = mBounds.top + mHalfArrowHeight + mCornerRadius;
                        break;
                    case Gravity.BOTTOM:
                        y = mBounds.bottom - mHalfArrowHeight - mCornerRadius;
                        break;
                    default:
                        y = mBounds.centerY() + mArrowDy;
                        break;
                }

                x1 = mBounds.left;
                y1 = y - mHalfArrowHeight;

                x2 = bounds.left;
                y2 = y;

                x3 = mBounds.left;
                y3 = y + mHalfArrowHeight;
                break;
            case ToLeft:
                // Tooltip to left, arrow on right
                mBounds.right -= mArrowWidth;

                switch(verticalGravity) {
                    case Gravity.TOP:
                        y = mBounds.top + mHalfArrowHeight + mCornerRadius;
                        break;
                    case Gravity.BOTTOM:
                        y = mBounds.bottom - mHalfArrowHeight - mCornerRadius;
                        break;
                    default:
                        y = mBounds.centerY() + mArrowDy;
                        break;
                }

                x1 = mBounds.right;
                y1 = y - mHalfArrowHeight;

                x2 = bounds.right;
                y2 = y;

                x3 = mBounds.right;
                y3 = y + mHalfArrowHeight;
                break;
            case Above:
                // Tooltip above, arrow below
                mBounds.bottom -= mArrowHeight;

                switch(horizontalGravity) {
                    case Gravity.LEFT:
                        x = mBounds.left + mHalfArrowWidth + mCornerRadius;
                        break;
                    case Gravity.RIGHT:
                        x = mBounds.right - mHalfArrowWidth - mCornerRadius;
                        break;
                    default:
                        x = mBounds.centerX() + mArrowDx;
                }

                x1 = x - mHalfArrowWidth;
                y1 = mBounds.bottom;

                x2 = x;
                y2 = bounds.bottom;

                x3 = x + mHalfArrowWidth;
                y3 = mBounds.bottom;
                break;
            default:
                // Tooltip below, arrow above
                // This is for a top center arrow
                mBounds.top += mArrowHeight;

                switch(horizontalGravity) {
                    case Gravity.LEFT:
                        x = mBounds.left + mHalfArrowWidth + mCornerRadius;
                        break;
                    case Gravity.RIGHT:
                        x = mBounds.right - mHalfArrowWidth - mCornerRadius;
                        break;
                    default:
                        x = mBounds.centerX() + mArrowDx;
                }

                //TODO: Convert from dp

                x1 = x - mHalfArrowWidth;
                y1 = mBounds.top;

                x2 = x;
                y2 = bounds.top;

                x3 = x + mHalfArrowWidth;
                y3 = mBounds.top;
                break;
        }

        mArrowPath = new Path();
        mArrowPath.moveTo(x1, y1);
        mArrowPath.lineTo(x2, y2);
        mArrowPath.lineTo(x3, y3);
        mArrowPath.close();
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw arrow
        canvas.drawPath(mArrowPath, mPaint);
        // Draw rect
        canvas.drawRoundRect(new RectF(mBounds), mCornerRadius, mCornerRadius, mPaint);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public boolean getPadding(Rect padding) {
        padding.bottom = mCornerRadius / 2;
        padding.top = mCornerRadius / 2;
        padding.left = mCornerRadius / 2;
        padding.right = mCornerRadius / 2;

        switch(mDirection) {
            case ToLeft:
                padding.right += mArrowWidth;
                break;
            case ToRight:
                padding.left += mArrowWidth;
                break;
            case Above:
                padding.bottom += mArrowHeight;
                break;
            case Below:
                padding.top += mArrowHeight;
                break;
        }
        return true;
    }

    public void setDirection(Direction direction) {
        mDirection = direction;
        updateArrowDimens();
    }

    private void updateArrowDimens() {
        if (mDirection == Direction.ToLeft || mDirection == Direction.ToRight) {
            mArrowHeight = mArrowSize;
            mHalfArrowHeight = mArrowHeight / 2;
            mArrowWidth = mArrowHeight / 2;
            mHalfArrowWidth = mArrowWidth / 2;
        } else {
            mArrowWidth = mArrowSize;
            mHalfArrowWidth = mArrowWidth / 2;
            mArrowHeight = mArrowWidth / 2;
            mHalfArrowHeight = mArrowHeight / 2;
        }
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setArrowOffsets(int arrowDx, int arrowDy) {
        mArrowDx = arrowDx;
        mArrowDy = arrowDy;
    }
}
