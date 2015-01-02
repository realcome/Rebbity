package com.rebbity.widget;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.Interpolator;
import android.view.animation.AccelerateInterpolator;

import com.rebbity.global.GlobalApp;
import com.rebbity.global.R;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Tyler on 15/1/2.
 */
public class RainbowAnime extends Drawable {
    private static final int COLORS_NUM = 4;
    public static final int STATE_IDLE = 0x0001;
    public static final int STATE_STARTING = STATE_IDLE << 1;
    public static final int STATE_ACTIVE = STATE_IDLE << 2;
    public static final int STATE_STOPPING_BEFORE_ACTIVE  = STATE_IDLE << 3;
    public static final int STATE_STOPPING = STATE_IDLE << 4;

    private static final Interpolator s_interpolator = new AccelerateInterpolator(1.6F);
    private static final int s_color_red;
    private static final int s_color_blue;
    private static final int s_color_green;
    private static final int s_color_gold;

    private static final Paint s_paint_red;
    private static final Paint s_paint_blue;
    private static final Paint s_paint_green;
    private static final Paint s_paint_gold;
    private static final Paint s_paint_gap;
    private static final Paint s_paint_line;

    private static final Paint[] s_color_paints;
    private static final float s_gapf = getsize(2.f);

    private final float mSplits[] = new float[COLORS_NUM];
    private final RainbowAnimeParams mTimeParams = new RainbowAnimeParams();


    private int mState;
    private long mStartTime;
    private long mTimeOffset;
    private int mEndTimePieceI;



    static {
        Resources localRes = GlobalApp.getG_context().getResources();
        s_color_red = localRes.getColor(R.color.rainbow_red);
        s_color_blue = localRes.getColor(R.color.rainbow_blue);
        s_color_green = localRes.getColor(R.color.rainbow_green);
        s_color_gold = localRes.getColor(R.color.rainbow_gold);

        s_paint_red = createPaint(s_color_red);
        s_paint_blue = createPaint(s_color_blue);
        s_paint_green = createPaint(s_color_green);
        s_paint_gold = createPaint(s_color_gold);

        s_paint_gap = createPaint(Color.TRANSPARENT);
        s_paint_line = createPaint(Color.parseColor("#22000000"));

        Paint[] arrPaint = new Paint[COLORS_NUM];

        arrPaint[0] = s_paint_green;
        arrPaint[1] = s_paint_blue;
        arrPaint[2] = s_paint_red;
        arrPaint[3] = s_paint_gold;

        s_color_paints = arrPaint;
    }

    private static float getsize(float size){
        DisplayMetrics dm = GlobalApp.getG_context().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, size, dm);
    }

    public RainbowAnime(Callback callback){
        Log.d("sdfsdfdsfdsfsdfsdf", "sdfsdfsdfds");
        setCallback(callback);
    }

    public void start(){
        this.mState = STATE_ACTIVE;
        this.mStartTime = SystemClock.uptimeMillis();
        invalidateSelf();
    }

    public void stop(){
        if (this.mState == STATE_IDLE || this.mState == STATE_STOPPING){
            return;
        }

        if (this.mState == STATE_STARTING){
            this.mState = STATE_STOPPING_BEFORE_ACTIVE;
        }else{
            this.mState = STATE_STOPPING;
        }

        RainbowAnimeParams.getParams(this.mTimeParams, this.mStartTime, this.mTimeOffset);
        this.mEndTimePieceI = this.mTimeParams.timePieceI;

        invalidateSelf();
    }

    private static void drawColorRect(Canvas canvas, float left, float top, float right, float bottom, Paint paint){
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawFrame(Canvas canvas, float left, float top, float right, float bottom){
        float f1 = right / 4.0F;
        drawColorRect(canvas, left + 0F, top, f1, bottom, s_paint_blue);
        drawColorRect(canvas, left + f1, top, f1, bottom, s_paint_green);
        drawColorRect(canvas, left + 2F * f1, top, f1, bottom, s_paint_red);
        drawColorRect(canvas, left + 3.0F * f1, top, f1, bottom, s_paint_gold);
    }

    private static void drawColorRect(Canvas canvas, float left, float top, float delta, float bottom, float limitLeft, float limitRight, Paint paint){
        float localLeft = left;

        if (localLeft < limitLeft){
            localLeft = limitLeft;
        }

        float localRight = localLeft + delta;

        if (localRight > limitRight){
            localRight = limitRight;
        }

        if (localLeft < localRight) {
            canvas.drawRect(localLeft, top, localRight, bottom, paint);
        }
    }

    private void drawFrame(Canvas canvas, float left, float top, float right, float bottom, float limitLeft, float limitRight){
        float f1 = right / 4.0F;
        drawColorRect(canvas, left + 0F, top, f1, bottom, limitLeft, limitRight, s_paint_blue);
        drawColorRect(canvas, left + f1, top, f1, bottom, limitLeft, limitRight, s_paint_green);
        drawColorRect(canvas, left + 2F * f1, top, f1, bottom, limitLeft, limitRight, s_paint_red);
        drawColorRect(canvas, left + 3.0F * f1, top, f1, bottom, limitLeft, limitRight, s_paint_gold);
    }

    public static void drawRect(){

    }

    @Override
    public void draw(Canvas canvas) {
        Rect paintRect = getBounds();
        float width = paintRect.width();
        float height = paintRect.height();
        float top = paintRect.top;
        float left = paintRect.left;


        if (this.mState == STATE_IDLE){
            drawFrame(canvas, left, top, width, height);
            canvas.drawLine(0.0f, height + 1.0f, width, height + 1.0f, s_paint_line);
            return;
        }

        Paint paintNow;
        Paint paintNext;
        float[] localSplits = this.mSplits;
        int split = localSplits.length;
        float fraction = 1.f / (float)split;
        RainbowAnimeParams timeParams = this.mTimeParams;

        int paintIndex;
        float offsetLeft;

        RainbowAnimeParams.getParams(timeParams, this.mStartTime, this.mTimeOffset);

        if (this.mState == STATE_STARTING && timeParams.timePieceI > 1){
            this.mState = STATE_ACTIVE;
        }else if (this.mState == STATE_STOPPING_BEFORE_ACTIVE && timeParams.timePieceI > this.mEndTimePieceI){
            this.mState = STATE_IDLE;
        }

        for (int i = 0; i < split; i++) {
            float input = (float)i * fraction + timeParams.rate;
            if (input > 1.0f){
                input--;
            }

            localSplits[i] = width * s_interpolator.getInterpolation(input);
            localSplits[i] -= s_gapf; // i color piece width.
        }

        float leftI0 = localSplits[0];
        Log.d("sss:", "f6:"+ leftI0);
        Arrays.sort(localSplits);

        // get paint index by time piece num.
        if (timeParams.timePieceI > s_color_paints.length - 1){
            paintIndex = timeParams.timePieceI % s_color_paints.length;
        }else{
            paintIndex = timeParams.timePieceI;
        }

        if (this.mState == STATE_STARTING || this.mState == STATE_STOPPING_BEFORE_ACTIVE){
            paintNow = null;
            drawFrame(canvas, left, top, width, height, left + leftI0, width + left);
        }else{
            paintNow = s_color_paints[paintIndex];
        }

        if (this.mState == STATE_STOPPING && timeParams.timePieceI > this.mEndTimePieceI){
            // stopping action.
            float delta = s_interpolator.getInterpolation(timeParams.rate) * 1.75f * width;
            drawFrame(canvas, left, top, width, height, left, delta + left + leftI0);
            paintNext = null;
            offsetLeft = left + leftI0;

        }else{
            int paintNextIndex;

            if (paintIndex < s_color_paints.length - 1){
                paintNextIndex = paintIndex + 1;
            }else{
                paintNextIndex = 0;
            }

            paintNext = s_color_paints[paintNextIndex];
            offsetLeft = left;
        }

        // now draw every piece.
        for (int i = 0; i < localSplits.length; i++) {
            float widthPre; //  i piece real width, including gap.
            if (i == 0){
                widthPre = 0.0f;
            }else{
                widthPre = localSplits[i - 1] + s_gapf;

                if(widthPre < 0.0F)
                    widthPre = 0.0F;
            }

            if(paintNow != null || widthPre < leftI0){ // if pre piece width is litter than first piece, ignore drawing it.
                continue;
            }

            float lastPieceWidth; // It is the max piece in localSplits arr.
            lastPieceWidth = localSplits[localSplits.length - 1] + s_gapf;

            if (lastPieceWidth < width){
                Paint drawPaint;

                if (lastPieceWidth >= leftI0){
                    drawPaint = paintNow;
                }else{
                    drawPaint = paintNext;
                }

                canvas.drawRect(offsetLeft + lastPieceWidth, top, width, top + height, drawPaint);

                Paint drawi;
                if (localSplits[i] > widthPre){
                    if (localSplits[i] >= leftI0){
                        drawi = paintNow;
                    }else{
                        drawi = paintNext;
                    }

                    canvas.drawRect(offsetLeft + widthPre, top, offsetLeft + localSplits[i], top + height, drawi);
                }
            }
        }

    }

    @Override
    public void setAlpha(int alpha) {

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
        this.mTimeOffset = (long)(1800.f * ((float)bounds.width() / getsize(320.f)));
        invalidateSelf();

    }

    private static Paint createPaint(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        return paint;
    }

}
