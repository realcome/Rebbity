/*
 * Copyright (C) 2015 The Rebbity Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rebbity.colorswitch;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CompoundButton;

/**
 * Created by Tyler on 15/1/17.
 */
public class ColorSwitch  extends CompoundButton implements View.OnClickListener{
    private final static int THUMB_ANIMATION_DURATION = 300;
    private final static int TOUCH_MODE_IDLE = 0;
    private final static int TOUCH_MODE_DOWN = 1;
    private final static int TOUCH_MODE_DRAGGING = 2;
    private boolean mAnimRunning;
    private Drawable mIdleThumbDrawable;
    private int mAnimationLength;

    private int mMinFlingVelocity;
    private Drawable mOffThumbDrawable;
    private int mThumbLeftMargin;
    private int mOffThumbWidth;

    private int mOnThumbDrawAlpha;
    private Drawable mOnThumbDrawable;
    private int mThumbRightMargin;
    private int mOnThumbWidth;

    private int mSwitchMinWidth;
    private int mSwitchPadding;

    /**
     * Width required to draw the switch track and thumb. Includes padding and
     * optical bounds for both the track and thumb.
     */
    private int mSwitchWidth;

    /**
     * Height required to draw the switch track and thumb. Includes padding and
     * optical bounds for both the track and thumb.
     */
    private int mSwitchHeight;

    /** Left bound for drawing the switch track and thumb. */
    private int mSwitchLeft;

    /** Top bound for drawing the switch track and thumb. */
    private int mSwitchTop;

    /** Right bound for drawing the switch track and thumb. */
    private int mSwitchRight;

    /** Bottom bound for drawing the switch track and thumb. */
    private int mSwitchBottom;

    private Rect mTempRect = new Rect();
    private int mThumbDrawWidth;
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbWidth;

    private Drawable mTrackDrawable;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    private Animation mPositionAnimator;

    /**
     * Construct a new Switch with default styling.
     *
     * @param context The Context that will determine this widget's theming.
     */
    public ColorSwitch(Context context) {
        this(context, null);
    }

    /**
     * Construct a new Switch with default styling, overriding specific style
     * attributes as requested.
     *
     * @param context The Context that will determine this widget's theming.
     * @param attrs Specification of attributes that should deviate from default styling.
     */
    public ColorSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Construct a new Switch with a default style determined by the given theme attribute,
     * overriding specific style attributes as requested.
     *
     * @param context The Context that will determine this widget's theming.
     * @param attrs Specification of attributes that should deviate from the default styling.
     * @param defStyleAttr An attribute in the current theme that contains a
     *        reference to a style resource that supplies default values for
     *        the view. Can be 0 to not look for defaults.
     */
    public ColorSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAnimRunning = false;
        mThumbPosition = 0.0f;
        this.setOnClickListener(this);

        final Resources res = getResources();
        final TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ColorSwitch, defStyleAttr, 0);
        mIdleThumbDrawable = arr.getDrawable(R.styleable.ColorSwitch_idleThumbDrawable);
        mThumbDrawable = mIdleThumbDrawable;

        mOnThumbDrawable = arr.getDrawable(R.styleable.ColorSwitch_onThumbDrawable);
        mOffThumbDrawable = arr.getDrawable(R.styleable.ColorSwitch_offThumbDrawable);;
        mTrackDrawable = arr.getDrawable(R.styleable.ColorSwitch_trackDrawable);
        mSwitchMinWidth = arr.getDimensionPixelOffset(R.styleable.ColorSwitch_minWidth, 0);
        mSwitchPadding = arr.getDimensionPixelOffset(R.styleable.ColorSwitch_padding, 0);
        arr.recycle();

        final ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        mMinFlingVelocity = config.getScaledMinimumFlingVelocity();

        setClickable(true);
        refreshDrawableState();
        setEnabled(isEnabled());
        setState(isChecked());
    }

    private int calcDrawWidth(float paramFloat) {
        float f1 = this.mThumbLeftMargin;
        float f2 = (this.mOnThumbWidth - this.mOffThumbWidth) / (0.1f + this.mAnimationLength + this.mThumbLeftMargin);
        return (int)(this.mOffThumbWidth - f1 * f2 + paramFloat * f2);
    }

    private int calcOnThumbDrawAlpha(float position) {
        return (int)((position) * 255.f / (getThumbScrollRange() + 0.1f));
    }

    private void animateThumbToCheckedState(boolean newCheckedState) {
        final float startPosition = mThumbPosition;
        final float targetPosition = newCheckedState ? getThumbScrollRange() : 0;
        final float diff = targetPosition - startPosition;
        final boolean state = newCheckedState;

        mPositionAnimator = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                float newPosition = startPosition + (diff * interpolatedTime);

                final float newPos  = Math.max(0, Math.min(newPosition, getThumbScrollRange()));
                mThumbDrawWidth = calcDrawWidth(newPos);
                mOnThumbDrawAlpha = calcOnThumbDrawAlpha(newPos);
                setThumbPosition(newPos);
                invalidate();
            }

        };

        mPositionAnimator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mAnimRunning = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimRunning = false;
                setChecked(state);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mPositionAnimator.setDuration(THUMB_ANIMATION_DURATION);
        startAnimation(mPositionAnimator);
    }

    private void cancelSuperTouch(MotionEvent ev) {
        MotionEvent cancel = MotionEvent.obtain(ev);
        cancel.setAction(MotionEvent.ACTION_CANCEL);
        super.onTouchEvent(cancel);
        cancel.recycle();
    }

    private boolean getTargetCheckedState() {
        //return false;
        return this.mThumbPosition >= (getThumbScrollRange() + this.mThumbRightMargin) / 2;
    }

    private int getThumbScrollRange() {
        if (mTrackDrawable != null) {
            final Rect padding = mTempRect;
            mTrackDrawable.getPadding(padding);
            return mSwitchWidth - mThumbWidth - padding.left - padding.right - mThumbRightMargin - mThumbLeftMargin;
        } else {
            return 0;
        }
    }

    /**
     * @return true if (x, y) is within the target area of the switch
     */
    private boolean hitSwitch(float x, float y)
    {
        return ((x > this.mSwitchLeft) && (x < this.mSwitchRight) && (y > this.mSwitchTop) && (y < this.mSwitchBottom));
    }

    /**
     * Sets the thumb position as a decimal value between 0 (off) and 1 (on).
     *
     * @param position new position between [0,1]
     */
    private void setThumbPosition(float position) {
        mThumbPosition = position;
    }

    /**
     * Called from onTouchEvent to end a drag operation.
     *
     * @param ev Event that triggered the end of drag mode - ACTION_UP or ACTION_CANCEL
     */
    private void stopDrag(MotionEvent ev) {
        mTouchMode = TOUCH_MODE_IDLE;

        // Commit the change if the event is up and not canceled and the switch
        // has not been disabled during the drag.
        final boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP && isEnabled();
        boolean newState = false;
        if (commitChange) {
            mVelocityTracker.computeCurrentVelocity(1000);
            final float xvel = mVelocityTracker.getXVelocity();
            if (Math.abs(xvel) > mMinFlingVelocity) {
                newState = getTargetCheckedState();
            }
        } else {
            newState = isChecked();
        }

        setState(newState);
        cancelSuperTouch(ev);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        final int[] myDrawableState = getDrawableState();

        if (mThumbDrawable != null) {
            mThumbDrawable.setState(myDrawableState);
        }

        if (mTrackDrawable != null) {
            mTrackDrawable.setState(myDrawableState);
        }

        if (mOnThumbDrawable != null) {
            mOnThumbDrawable.setState(myDrawableState);
        }

        if (mIdleThumbDrawable != null) {
            mIdleThumbDrawable.setState(myDrawableState);
        }

        invalidate();
    }

    @Override
    public int getCompoundPaddingLeft() {
        int padding = super.getCompoundPaddingLeft() + mSwitchWidth;
        return padding;
    }

    @Override
    public int getCompoundPaddingRight() {
        int padding = super.getCompoundPaddingRight() + mSwitchWidth;
        return padding;
    }

    /**
     * Set the amount of horizontal padding between the switch and the associated text.
     *
     * @param pixels Amount of padding in pixels
     */
    public void setSwitchPadding(int pixels) {
        mSwitchPadding = pixels;
        requestLayout();
    }

    /**
     * Get the amount of horizontal padding between the switch and the associated text.
     *
     * @return Amount of padding in pixels
     */
    public int getSwitchPadding() {
        return mSwitchPadding;
    }

    /**
     * Set the minimum width of the switch in pixels. The switch's width will be the maximum
     * of this value and its measured width as determined by the switch drawables and text used.
     *
     * @param pixels Minimum width of the switch in pixels
     */
    public void setSwitchMinWidth(int pixels) {
        mSwitchMinWidth = pixels;
        requestLayout();
    }

    /**
     * Get the minimum width of the switch in pixels. The switch's width will be the maximum
     * of this value and its measured width as determined by the switch drawables and text used.
     *
     * @return Minimum width of the switch in pixels
     */
    public int getSwitchMinWidth() {
        return mSwitchMinWidth;
    }

    /**
     * Set the drawable used for the track that the switch slides within.
     *
     * @param track Track drawable
     */
    public void setTrackDrawable(Drawable track) {
        mTrackDrawable = track;
        requestLayout();
    }

    /**
     * Set the drawable used for the track that the switch slides within.
     *
     * @param resId Resource ID of a track drawable
     */
    public void setTrackResource(int resId) {
        setTrackDrawable(getResources().getDrawable(resId));
    }

    /**
     * Get the drawable used for the track that the switch slides within.
     *
     * @return Track drawable
     */
    public Drawable getTrackDrawable() {
        return mTrackDrawable;
    }

    public boolean ismAnimRunning() {
        return mAnimRunning;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        if (Build.VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();

            if (mThumbDrawable != null) {
                mThumbDrawable.jumpToCurrentState();
            }

            if (mTrackDrawable != null) {
                mTrackDrawable.jumpToCurrentState();
            }
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ColorSwitch.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(ColorSwitch.class.getName());
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
    }

    public void setState(boolean checked) {
        if (mAnimRunning) {
            return;
        }


        if (getWindowToken() != null) {
            animateThumbToCheckedState(checked);
        } else {
            // Immediately move the thumb to the new position.
            cancelPositionAnimator();
            setChecked(checked);
        }
    }

    /**
     * Set the drawable used for the switch "thumb" - the piece that the user
     * can physically touch and drag along the track.
     *
     * @param thumb Thumb drawable
     */
    public void setThumbDrawable(Drawable thumb) {
        mThumbDrawable = thumb;
        requestLayout();
    }

    /**
     * Set the drawable used for the switch "thumb" - the piece that the user
     * can physically touch and drag along the track.
     *
     * @param resId Resource ID of a thumb drawable
     */
    public void setThumbResource(int resId) {
        setThumbDrawable(getResources().getDrawable(resId));
    }

    /**
     * Get the drawable used for the switch "thumb" - the piece that the user
     * can physically touch and drag along the track.
     *
     * @return Thumb drawable
     */
    public Drawable getThumbDrawable() {
        return mThumbDrawable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mOnThumbWidth = mOnThumbDrawable.getIntrinsicWidth();
        mOffThumbWidth = mOffThumbDrawable.getIntrinsicWidth();
        mThumbWidth = mOnThumbWidth;
        this.mSwitchWidth = this.mTrackDrawable.getIntrinsicWidth();
        this.mSwitchHeight = this.mTrackDrawable.getIntrinsicHeight();
        this.mThumbLeftMargin = (Math.max(0, this.mSwitchHeight - this.mOffThumbWidth) / 2);
        this.mThumbRightMargin = (Math.max(0, this.mSwitchHeight - this.mOnThumbWidth) / 2);
        this.mAnimationLength = (this.mSwitchWidth - this.mOnThumbWidth - this.mThumbLeftMargin + this.mThumbRightMargin);

        int thumbWidth;
        if (isChecked()) {
            thumbWidth = mOnThumbWidth;
        }else {
            thumbWidth = mOffThumbWidth;
        }

        mThumbDrawWidth = thumbWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int measuredHeight = getMeasuredHeight();
        if (measuredHeight < mSwitchHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), this.mSwitchHeight);
        }
    }

    /**
     * @return true if (x, y) is within the target area of the switch thumb
     */
    private boolean hitThumb(float x, float y) {
        float limitleft = Math.max(mSwitchLeft, mSwitchLeft + mTempRect.left + mThumbPosition + mThumbLeftMargin);
        float limitright = limitleft + mThumbWidth;
        float limittop = mSwitchTop + mTempRect.top + mThumbLeftMargin; // replace mThumbLeftMargin.
        float limitbottom = limittop + mThumbWidth;

        return x >= limitleft && x <= limitright && y >= limittop && y <= limitbottom;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        final int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();

                if (isEnabled() && hitSwitch(x, y)) {
                    mTouchMode = TOUCH_MODE_DOWN;
                    mTouchX = x;
                    mTouchY = y;
                    setClickable(false);
                    //setPressed(true);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                switch (mTouchMode) {
                    case TOUCH_MODE_IDLE:
                        // Didn't target the thumb, treat normally.
                        break;

                    case TOUCH_MODE_DOWN: {
                        final float x = event.getX();
                        final float y = event.getY();
                        if (Math.abs(x - mTouchX) > mTouchSlop ||
                                Math.abs(y - mTouchY) > mTouchSlop) {
                            mTouchMode = TOUCH_MODE_DRAGGING;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            mTouchX = x;
                            mTouchY = y;
                            return true;
                        }
                        break;
                    }
                    case TOUCH_MODE_DRAGGING: {
                        final float x = event.getX();

                        final float thumbScrollOffset = x - mTouchX;
                        final float newPos  = Math.max(0, Math.min(thumbScrollOffset + this.mThumbPosition, getThumbScrollRange()));

                        if (newPos != mThumbPosition) {
                            mTouchX = x;
                            mThumbDrawWidth = calcDrawWidth(newPos);
                            mOnThumbDrawAlpha = calcOnThumbDrawAlpha(newPos);
                            setThumbPosition(newPos);
                            invalidate();
                            return true;
                        }
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                setClickable(true);
                //setPressed(false);

                if (mTouchMode == TOUCH_MODE_DRAGGING) {
                    stopDrag(event);
                    // Allow super class to handle pressed state, etc.
                    super.onTouchEvent(event);
                    return true;
                }else if (mTouchMode == TOUCH_MODE_DOWN) {
                    toggle();
                }
                mTouchMode = TOUCH_MODE_IDLE;
                mVelocityTracker.clear();
                break;
            }
        }

        super.onTouchEvent(event);
        return isEnabled();
    }

    private void cancelPositionAnimator() {
        if (mPositionAnimator != null) {
            mPositionAnimator.cancel();
            mPositionAnimator = null;
        }
    }

    @Override
    public void toggle() {
        setState(!isChecked());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!(this.mAnimRunning))
        {
            boolean checked = isChecked();
            setThumbPosition(checked ? getThumbScrollRange() : 0.0f);
            if (!checked)
                this.mOnThumbDrawAlpha = 0;
            else
                this.mOnThumbDrawAlpha = 255;
        }

        int opticalInsetLeft = 0;
        int opticalInsetRight = 0;
        if (mThumbDrawable != null) {
            final Rect trackPadding = mTempRect;
            if (mTrackDrawable != null) {
                mTrackDrawable.getPadding(trackPadding);
            } else {
                trackPadding.setEmpty();
            }

            opticalInsetLeft = 0;
            opticalInsetRight = 0;
        }

        final int switchRight;
        final int switchLeft;
        switchRight = getWidth() - getPaddingRight() - opticalInsetRight;
        switchLeft = switchRight - mSwitchWidth + opticalInsetLeft + opticalInsetRight;

        final int switchTop;
        final int switchBottom;
        switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
            default:
            case Gravity.TOP:
                switchTop = getPaddingTop();
                switchBottom = switchTop + mSwitchHeight;
                break;

            case Gravity.CENTER_VERTICAL:
                switchTop = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 -
                        mSwitchHeight / 2;
                switchBottom = switchTop + mSwitchHeight;
                break;

            case Gravity.BOTTOM:
                switchBottom = getHeight() - getPaddingBottom();
                switchTop = switchBottom - mSwitchHeight;
                break;
        }

        mSwitchLeft = switchLeft;
        mSwitchTop = switchTop;
        mSwitchBottom = switchBottom;
        mSwitchRight = switchRight;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = mSwitchLeft;
        int top = mSwitchTop;
        int right = mSwitchRight;
        int bottom = mSwitchBottom;

        mTrackDrawable.setBounds(left, top, right, bottom);
        mTrackDrawable.draw(canvas);
        canvas.save();
        mTrackDrawable.getPadding(mTempRect);
        int innerLeft = mSwitchLeft + mTempRect.left + mThumbLeftMargin;
        int innerRight = mSwitchRight - mTempRect.right - mThumbRightMargin;
        int innerTop = mSwitchTop + mTempRect.top;
        int innerBottom = mSwitchBottom - mTempRect.bottom;

        canvas.clipRect(innerLeft, innerTop, innerRight, innerBottom);
        mThumbDrawable.getPadding(mTempRect);
//
        int posX = Math.round(mThumbPosition);
        int thumbLeft = posX + innerLeft - this.mTempRect.left;
        int thumbRight = innerLeft + posX + this.mThumbDrawWidth + this.mTempRect.right;
        int thumbTop = innerTop + (this.mSwitchHeight - this.mThumbDrawWidth) / 2;
        int thumbBottom = thumbTop + this.mThumbDrawWidth;
        if ((this.mAnimRunning) || (this.mTouchMode == TOUCH_MODE_DRAGGING))
        {
            this.mOffThumbDrawable.setBounds(thumbLeft, thumbTop, thumbRight, thumbBottom);
            this.mOffThumbDrawable.draw(canvas);
            this.mOnThumbDrawable.setAlpha(this.mOnThumbDrawAlpha);
            this.mOnThumbDrawable.setBounds(thumbLeft, thumbTop, thumbRight, thumbBottom);
            this.mOnThumbDrawable.draw(canvas);
        }else {
            this.mThumbDrawable = this.mIdleThumbDrawable;
            this.mThumbDrawable.setBounds(thumbLeft, thumbTop, thumbRight, thumbBottom);
            this.mThumbDrawable.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mThumbDrawable || who == mTrackDrawable;
    }

    @Override
    public void onClick(View v) {
        toggle();
    }
}
