package com.kabuko.tooltip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by franciskam on 1/30/15.
 */
public class Tooltip {
    public static final int LONG_DELAY = 3500; // 3.5 seconds
    public static final int SHORT_DELAY = 2000; // 2 seconds

    private PopupWindow mPopupWindow;

    private int mGravity;
    private Direction mTooltipDirection = Direction.Auto;
    private CharSequence mText;
    private long mDelay = SHORT_DELAY;
    private View mView;
    private Typeface mTypeface;
    private int mColor = Color.argb(250, 160, 160, 160);

    public Tooltip() {
    }

    /**
     * Delay in milliseconds before automatically dismissing. Defaults to SHORT_DELAY (2s).
     * Set to 0 to never auto-dismiss.
     *
     * @param delay
     */
    public void setDelay(long delay) {
        mDelay = delay;
    }

    /**
     * Set text to display.
     *
     * If you set your own custom view with setContentView, you will have to set any text on your
     * own instead of using this property.
     *
     * @param text Text to display.
     */
    public void setText(CharSequence text) {
        mText = text;
    }

    public void setBackgroundColor(int color) {
        mColor = color;
    }

    public void show(View anchor) {
        show(anchor, 0, 0);
    }

    public void show(View anchor, int dx, int dy) {
        Resources res = anchor.getResources();
        Context context = anchor.getContext();

        Rect windowRect = new Rect();
        anchor.getWindowVisibleDisplayFrame(windowRect);

        int cornerRadius = res.getDimensionPixelSize(R.dimen.tooltip_corner_radius);;
        int arrowSize = res.getDimensionPixelSize(R.dimen.tooltip_arrow_size);

        Direction direction = mTooltipDirection;

        // If auto, start with assuming tooltip is below the anchor
        if (direction == Direction.Auto) {
            direction = Direction.Below;
        }

        View view = mView;

        TooltipDrawable drawable = null;
        if (view == null) {
            view = View.inflate(context, R.layout.tooltip, null);
            drawable = new TooltipDrawable(mColor, cornerRadius, arrowSize, direction, mGravity);
            view.setBackgroundDrawable(drawable);

            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(mText);

            textView.setTypeface(mTypeface);
        }

        // Pre-measure view
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        // Find the measured tooltip bottom
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        int y = location[1];
        int anchorBottom = y + anchor.getHeight();
        int viewBottom = anchorBottom + dy + view.getMeasuredHeight();

        // If the tooltip doesn't fit below, move it above instead
        if (mTooltipDirection == Direction.Auto && viewBottom > windowRect.bottom) {
            direction = Direction.Above;
        }

        int anchorWidth = anchor.getWidth();
        int anchorHeight = anchor.getHeight();
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        int finalDy = 0;
        int finalDx = 0;

        int verticalGravity = Gravity.VERTICAL_GRAVITY_MASK & mGravity;
        int horizontalGravity = Gravity.HORIZONTAL_GRAVITY_MASK & mGravity;

        int arrowDx = 0;
        int arrowDy = 0;

        int[] anchorLocation = new int[2];
        anchor.getLocationInWindow(anchorLocation);
        int anchorInWindowX = anchorLocation[0];
        int anchorInWindowCenterX = anchorInWindowX + anchor.getWidth() / 2;
        int anchorInWindowY = anchorLocation[1];
        int anchorInWindowCenterY = anchorInWindowY + anchor.getHeight() / 2;

        if (direction == Direction.Above || direction == Direction.Below) {
            switch (horizontalGravity) {
                case Gravity.LEFT:
                    finalDx = dx;
                    break;
                case Gravity.RIGHT:
                    finalDx = anchorWidth - viewWidth + dx;
                    break;
                default:
                    // CENTER_HORIZONTAL (Default)
                    finalDx = (anchorWidth - viewWidth) / 2 + dx;

                    if (anchorInWindowCenterX - viewWidth / 2 < windowRect.left) {
                        // If tooltip would have gone past the left, adjust the arrow to center on the anchor
                        int oldArrowX = windowRect.left + viewWidth / 2;
                        int arrowX = (anchor.getLeft() + anchor.getRight()) / 2;
                        arrowDx = arrowX - oldArrowX;
                    } else if (anchorInWindowCenterX + viewWidth / 2 > windowRect.right) {
                        // If tooltip would have gone past the right, adjust the arrow to center on the anchor
                        int oldArrowX = windowRect.right - viewWidth / 2;
                        int arrowX = (anchor.getLeft() + anchor.getRight()) / 2;
                        arrowDx = arrowX - oldArrowX;
                    }
            }
        } else if (direction == Direction.ToLeft || direction == Direction.ToRight) {
            switch (verticalGravity) {
                case Gravity.TOP:
                    finalDy = dy - anchorHeight;
                    break;
                case Gravity.BOTTOM:
                    finalDy = dy - viewHeight;
                    break;
                default:
                    // CENTER_VERTICAL (Default)
                    finalDy = dy - (viewHeight + anchorHeight) / 2;

                    if (anchorInWindowCenterY - viewHeight / 2 < windowRect.top) {
                        // If tooltip would have gone past the top, adjust the arrow to center on the anchor
                        int oldArrowY = windowRect.top + viewHeight / 2;
                        int arrowY = (anchor.getTop() + anchor.getBottom()) / 2;
                        arrowDy = arrowY - oldArrowY;
                    } else if (anchorInWindowCenterY - viewHeight / 2 < windowRect.top) {
                        // If tooltip would have gone past the top, adjust the arrow to center on the anchor
                        int oldArrowY = windowRect.bottom - viewHeight / 2;
                        int arrowY = (anchor.getTop() + anchor.getBottom()) / 2;
                        arrowDy = arrowY - oldArrowY;
                    }
            }
        }

        switch (direction) {
            case ToLeft:
                finalDx = dx - viewWidth;
                break;
            case ToRight:
                finalDx = dx + anchorWidth;
                break;
            case Above:
                finalDy = dy - anchorHeight - viewHeight;
                break;
            case Below:
                finalDy = dy;
                break;
            default:
                // Should never get here. Should have chosen a direction already.
                throw new UnsupportedOperationException("Invalid direction encountered.");
        }

        if (mView == null && drawable != null) {
            // Need to set to null and back again to reset padding
            view.setBackgroundDrawable(null);
            drawable.setDirection(direction);
            drawable.setArrowOffsets(arrowDx, arrowDy);
            view.setBackgroundDrawable(drawable);
        }

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAsDropDown(anchor, finalDx, finalDy);

        if (mDelay > 0) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPopupWindow.dismiss();
                }
            }, mDelay);
        }
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    public void setTooltipDirection(Direction tooltipDirection) {
        mTooltipDirection = tooltipDirection;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }

    public static enum Direction {
        Auto,
        Above,
        ToLeft,
        ToRight,
        Below
    }
}
