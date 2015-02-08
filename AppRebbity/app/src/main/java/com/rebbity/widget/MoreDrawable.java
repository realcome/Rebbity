package com.rebbity.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.rebbity.cn.RebbityApp;

/**
 * Created by Tyler on 15/2/7.
 */
public class MoreDrawable extends Drawable {

    public final static int TYPE_CIRCLE = 0x0001;
    public final static int TYPE_RECT = 0x0002;

    public final static int TYPE_RECT_ROWS = 4;
    public final static float TYPE_RECT_ROWS_GAP = getsize(3.f);
    public final static int TYPE_RECT_COLUMNS = 3;
    public final static float TYPE_RECT_COLUMNS_GAP = getsize(4.f);

    private static final Interpolator g_interpolator = new AccelerateDecelerateInterpolator();

    private static final int ANIMATION_TIME_ID = android.R.integer.config_mediumAnimTime;
    private int mType = TYPE_RECT;

    private int mColor = Color.GRAY;

    private boolean mState = false;

    private Paint mPaint;

    private ObjectAnimator objectAnimator1;

    private ObjectAnimator objectAnimator2;

    private ObjectAnimator objectAnimator3;

    public float getAnimationProgress1() {
        return animationProgress1;
    }

    public void setAnimationProgress1(float animationProgress1) {
        this.animationProgress1 = animationProgress1;
        invalidateSelf();
    }

    public float getAnimationProgress2() {
        return animationProgress2;
    }

    public void setAnimationProgress2(float animationProgress2) {
        this.animationProgress2 = animationProgress2;
        invalidateSelf();
    }

    public float getAnimationProgress3() {
        return animationProgress3;
    }

    public void setAnimationProgress3(float animationProgress3) {
        this.animationProgress3 = animationProgress3;
        invalidateSelf();
    }

    private float animationProgress1;
    private float animationProgress2;
    private float animationProgress3;

    private float item1_x_start;
    private float item1_x_end;

    private float item2_x_start;
    private float item2_x_end;

    private float item3_x_start;
    private float item3_x_end;

    private boolean isRunning = false;

    private boolean hasInit = false;

    public MoreDrawable(Callback callback) {
        final int pressedAnimationTime = RebbityApp.getG_context().getResources().getInteger(ANIMATION_TIME_ID);
        setCallback(callback);

        objectAnimator1 = ObjectAnimator.ofFloat(this, "animationProgress1", 0f, 0f);
        objectAnimator1.setDuration((int)(pressedAnimationTime * 1.5));

        objectAnimator2 = ObjectAnimator.ofFloat(this, "animationProgress2", 0f, 0f);
        objectAnimator2.setDuration(pressedAnimationTime);

        objectAnimator3 = ObjectAnimator.ofFloat(this, "animationProgress3", 0f, 0f);
        objectAnimator3.setDuration(pressedAnimationTime * 2);

        objectAnimator1.setInterpolator(g_interpolator);
        objectAnimator2.setInterpolator(g_interpolator);
        objectAnimator3.setInterpolator(g_interpolator);

        objectAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationProgress1 = (float) animation.getAnimatedValue();
                upate();
            }
        });

        objectAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationProgress2 = (float) animation.getAnimatedValue();
                upate();
            }
        });

        objectAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationProgress3 = (float) animation.getAnimatedValue();
                Log.e("_____ANIMATE___", ""+animationProgress1);
                upate();
            }
        });


        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    private static int getsize(float size){
        DisplayMetrics dm = RebbityApp.getG_context().getResources().getDisplayMetrics();
        return (int)TypedValue.applyDimension(1, size, dm);
    }

    private boolean isRunning() {
        return objectAnimator1.isRunning() && objectAnimator2.isRunning() && objectAnimator3.isRunning();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    public void upate() {
        this.invalidateSelf();


        Log.e("------------", "______UPDATE__");
    }

    /**
     * draw animation
     * if Type is rect, will do like this:
     * - - -          - - -
     * - - -  =====>  - - -
     * - -              - -
     * -                  -
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();

        float width = rect.width();
        float height =  rect.height();
        float left = rect.left;
        float top = rect.top;

        float edge = Math.min(width, height);



        if (mType == TYPE_RECT) {
            float item_w = (edge - (TYPE_RECT_COLUMNS - 1) * TYPE_RECT_COLUMNS_GAP) / TYPE_RECT_COLUMNS;
            float item_h = (edge - (TYPE_RECT_ROWS - 1) * TYPE_RECT_ROWS_GAP) / TYPE_RECT_ROWS;

            // draw static rect 2 * 3
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    canvas.drawRect(left + j * (TYPE_RECT_COLUMNS_GAP + item_w),
                            top + i * (TYPE_RECT_ROWS_GAP + item_h),
                            left + j * (TYPE_RECT_COLUMNS_GAP + item_w) + item_w,
                            top + i * (TYPE_RECT_ROWS_GAP + item_h) + item_h, mPaint);
                }
            }

            float item_1_2_y = top + 2 * (TYPE_RECT_ROWS_GAP + item_h);
            float item_3_y = top + 3 * (TYPE_RECT_ROWS_GAP + item_h);

            if (!hasInit) {
                if (mState == false) {
                    item1_x_start = left;
                    item1_x_end = left + TYPE_RECT_COLUMNS_GAP + item_w;

                    item2_x_start = left + TYPE_RECT_COLUMNS_GAP + item_w;
                    item2_x_end = left + 2 * (TYPE_RECT_COLUMNS_GAP + item_w);

                    item3_x_start = left;
                    item3_x_end = left + 2 * (TYPE_RECT_COLUMNS_GAP + item_w);
                } else {
                    item1_x_end = left;
                    item1_x_start = left + TYPE_RECT_COLUMNS_GAP + item_w;

                    item2_x_end = left + TYPE_RECT_COLUMNS_GAP + item_w;
                    item2_x_start = left + 2 * (TYPE_RECT_COLUMNS_GAP + item_w);

                    item3_x_end = left;
                    item3_x_start = left + 2 * (TYPE_RECT_COLUMNS_GAP + item_w);
                }

                canvas.drawRect(item1_x_start, item_1_2_y, item1_x_start + item_w, item_1_2_y + item_h, mPaint);
                canvas.drawRect(item2_x_start, item_1_2_y, item2_x_start + item_w, item_1_2_y + item_h, mPaint);
                canvas.drawRect(item3_x_start, item_3_y, item3_x_start + item_w, item_3_y + item_h, mPaint);

                hasInit = true;
            } else {
                Log.e("------------", "ELSE");
                canvas.drawRect(animationProgress1, item_1_2_y, animationProgress1 + item_w, item_1_2_y + item_h, mPaint);
                canvas.drawRect(animationProgress2, item_1_2_y, animationProgress2 + item_w, item_1_2_y + item_h, mPaint);
                canvas.drawRect(animationProgress3, item_3_y, animationProgress3 + item_w, item_3_y + item_h, mPaint);
            }
        }
    }

    public void toggle() {
        animateStateChange(!mState);
    }

    public void animateStateChange(boolean newState) {
        if (isRunning()) {
            return;
        }

        float swaptmp;
        objectAnimator1.setFloatValues(item1_x_start, item1_x_end);
        objectAnimator2.setFloatValues(item2_x_start, item2_x_end);
        objectAnimator3.setFloatValues(item3_x_start, item3_x_end);

        objectAnimator1.start();
        objectAnimator2.start();
        objectAnimator3.start();

        swaptmp = item1_x_start;
        item1_x_start = item1_x_end;
        item1_x_end = swaptmp;

        swaptmp = item2_x_start;
        item2_x_start = item2_x_end;
        item2_x_end = swaptmp;

        swaptmp = item3_x_start;
        item3_x_start = item3_x_end;
        item3_x_end = swaptmp;



        mState = newState;
    }

    @Override
    public void setColorFilter(int color, PorterDuff.Mode mode) {
        super.setColorFilter(color, mode);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }
}
