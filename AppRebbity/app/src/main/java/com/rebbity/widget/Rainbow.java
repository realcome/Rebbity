package com.rebbity.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Interpolator;
import android.view.animation.AccelerateInterpolator;

import com.rebbity.cn.RebbityApp;
import com.rebbity.cn.R;

import java.util.Arrays;

/**
 * Created by Tyler on 15/1/2.
 */
public class Rainbow extends Drawable {
    private static final int PIECES_NUM = 4;
    public static final int STATE_IDLE = 0x0001;
    public static final int STATE_STARTING = STATE_IDLE << 1;
    public static final int STATE_ACTIVE = STATE_IDLE << 2;
    public static final int STATE_STOPPING_BEFORE_ACTIVE  = STATE_IDLE << 3;
    public static final int STATE_STOPPING = STATE_IDLE << 4;

    private static final Interpolator g_interpolator = new AccelerateInterpolator(1.6F);
    private static final int g_color_red;
    private static final int g_color_blue;
    private static final int g_color_green;
    private static final int g_color_gold;

    private static final Paint g_paint_red;
    private static final Paint g_paint_blue;
    private static final Paint g_paint_green;
    private static final Paint g_paint_gold;
    private static final Paint g_paint_line;

    private static final Paint[] g_color_paints;
    private static final float g_piece_gap = getsize(2.f);

    private final float m_pieces_width_arr[] = new float[PIECES_NUM];
    private final RainbowParams m_time_params = new RainbowParams();


    private int m_state;
    private long m_start_time;
    private long m_time_offset;
    private int m_stop_time_piece_i;

    private Paint m_paint_curr;
    private Paint m_paint_next;

    private float m_width_0;


    static {
        Resources localRes = RebbityApp.getG_context().getResources();
        g_color_red = localRes.getColor(R.color.rainbow_red);
        g_color_blue = localRes.getColor(R.color.rainbow_blue);
        g_color_green = localRes.getColor(R.color.rainbow_green);
        g_color_gold = localRes.getColor(R.color.rainbow_gold);

        g_paint_red = createPaint(g_color_red);
        g_paint_blue = createPaint(g_color_blue);
        g_paint_green = createPaint(g_color_green);
        g_paint_gold = createPaint(g_color_gold);

        g_paint_line = createPaint(Color.parseColor("#22000000"));

        Paint[] arrPaint = new Paint[PIECES_NUM];

        arrPaint[0] = g_paint_green;
        arrPaint[1] = g_paint_blue;
        arrPaint[2] = g_paint_red;
        arrPaint[3] = g_paint_gold;

        g_color_paints = arrPaint;
    }

    private static float getsize(float size){
        DisplayMetrics dm = RebbityApp.getG_context().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, size, dm);
    }

    public Rainbow(Callback callback){
        this.m_state = STATE_IDLE;
        this.m_paint_curr = null;
        this.m_paint_next = null;
        this.m_width_0 = 0;
        setCallback(callback);
    }

    private int getCirculationIndex(int index, int length){
        if (index < 0){
            return index + length;
        }

        if (index > length - 1){
            return index % length;
        }

        return index;
    }

    private void updatePaint(int time_piece_i){
        if (g_color_paints.length != 0){
            int index_curr;
            int index_next;

            index_curr = getCirculationIndex(time_piece_i - 1, g_color_paints.length);
            index_next = getCirculationIndex(index_curr + 1, g_color_paints.length);

            m_paint_curr = g_color_paints[index_curr];
            m_paint_next = g_color_paints[index_next];
        }
    }

    public void start(){
        this.m_state = STATE_STARTING;
        this.m_start_time = SystemClock.uptimeMillis();
        invalidateSelf();
    }

    public void stop(){
        if (this.m_state == STATE_IDLE || this.m_state == STATE_STOPPING){
            return;
        }

        if (this.m_state == STATE_STARTING){
            this.m_state = STATE_STOPPING_BEFORE_ACTIVE;
        }else{
            this.m_state = STATE_STOPPING;
        }

        RainbowParams.getParams(this.m_time_params, this.m_start_time, this.m_time_offset);
        this.m_stop_time_piece_i = this.m_time_params.timePieceI;

        invalidateSelf();
    }

    private static void drawColorRect(Canvas canvas, float left, float top, float right, float bottom, Paint paint){
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawColorBar(Canvas canvas, float left, float top, float right, float bottom) {
        int pieces = g_color_paints.length;

        float per_piece_width = (right - left) / (float) pieces;

        float local_left = 0.0f;

        for (int i = 0; i < pieces; i++) {
            local_left = left + (float) i * per_piece_width;
            drawColorRect(canvas, local_left, top, per_piece_width + local_left, bottom, g_color_paints[i]);
        }
    }

    private static void drawColorRect(Canvas canvas, float left, float top, float right, float bottom, float limitLeft, float limitRight, Paint paint){
        float localLeft = left;

        if (localLeft < limitLeft){
            localLeft = limitLeft;
        }

        float localRight = right;

        if (localRight > limitRight){
            localRight = limitRight;
        }

        if (localLeft < localRight) {
            canvas.drawRect(localLeft, top, localRight, bottom, paint);
        }
    }

    private void drawColorBar(Canvas canvas, float left, float top, float right, float bottom, float limit_left, float limit_right){
        int pieces = g_color_paints.length;

        float per_piece_width = (right - left) / (float) pieces;

        float local_left = 0.0f;

        for (int i = 0; i < pieces; i++) {
            local_left = left + (float) i * per_piece_width;
            drawColorRect(canvas, local_left, top, per_piece_width + local_left, bottom, limit_left, limit_right, g_color_paints[i]);
        }
    }

    private void updatePieceWidth(RainbowParams params, float bar_width){
        int size  = m_pieces_width_arr.length;
        float fraction = 1.0f / (float) size;

        for (int i = 0; i < size; i++) {
            float input = (float)i * fraction + params.rate;
            if (input > 1.0f){
                input--;
            }

            m_pieces_width_arr[i] = bar_width * g_interpolator.getInterpolation(input);
            m_pieces_width_arr[i] -= g_piece_gap; // i color piece width.
        }

        m_width_0 = m_pieces_width_arr[0];
        Arrays.sort(m_pieces_width_arr);
    }

    private void changeState(RainbowParams params){
        if(this.m_state != STATE_IDLE)
        {
            if(this.m_state == STATE_STARTING && params.timePieceI >= 1)
                this.m_state = STATE_ACTIVE;
            else if(this.m_state == STATE_STOPPING_BEFORE_ACTIVE && params.timePieceI == this.m_stop_time_piece_i)
                this.m_state = STATE_STOPPING;
            else if(this.m_state == STATE_STOPPING && params.timePieceI > this.m_stop_time_piece_i)
                this.m_state = STATE_IDLE;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        float width = rect.width();
        float height = rect.height();
        float top = rect.top;
        float left = rect.left;

        RainbowParams time_params = null;
        time_params = this.m_time_params;
        RainbowParams.getParams(time_params, this.m_start_time, this.m_time_offset);

        changeState(time_params);

        if(this.m_state == STATE_IDLE){
            drawColorBar(canvas, left, top, width, height);
            return;
        }

        updatePieceWidth(time_params, width);
        updatePaint(time_params.timePieceI);


        Paint paint_curr;
        Paint paint_next;
        float offset_left = 0.0f;
        float width_i_prev;
        int spiece_n = m_pieces_width_arr.length;

        float width_max_piece;

        if(this.m_state == STATE_STARTING || this.m_state == STATE_STOPPING_BEFORE_ACTIVE)
        {
            paint_curr = null;
            drawColorBar(canvas, left, top, width, height, left + m_width_0, left + width);
        } else
        {
            paint_curr = m_paint_curr;
        }

        if(this.m_state == STATE_STOPPING && time_params.timePieceI >= this.m_stop_time_piece_i)
        {
            float offset_left_stop = g_interpolator.getInterpolation(time_params.rate) * (1.75F * width);
            drawColorBar(canvas, left, top, width, height, left, offset_left_stop + (left + m_width_0));
            paint_next = null;
            offset_left = left + offset_left_stop;
        } else {
            paint_next = m_paint_next;
        }

        for (int k2 = 0; k2 < spiece_n; k2++) {
            if (k2 == 0){
                width_i_prev = 0.0f;
            }else{
                width_i_prev = m_pieces_width_arr[k2 - 1] + g_piece_gap;

                if (width_i_prev < 0.0f){
                    width_i_prev = 0.0f;
                }
            }

            width_max_piece = m_pieces_width_arr[spiece_n - 1] + g_piece_gap;

            if(width_max_piece < width)
            {


                Paint paint2;
                float f10;
                Paint paint3;
                if(width_max_piece >= m_width_0)
                    paint2 = paint_curr;
                else
                    paint2 = paint_next;
                if(paint2 != null) {
                    canvas.drawRect(offset_left + width_max_piece, top, width, top + height, paint2);
                }

                f10 = m_pieces_width_arr[k2];

                if(f10 > width_i_prev)
                {
                    if(width_i_prev >= m_width_0)
                        paint3 = paint_curr;
                    else
                        paint3 = paint_next;
                    if(paint3 != null) {
                        canvas.drawRect(offset_left + width_i_prev, top, offset_left + f10, top + height, paint3);
                    }
                }
            }
            
        }

        invalidateSelf();
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
        this.m_time_offset = (long)(1800.f * ((float)bounds.width() / getsize(320.f)));
        invalidateSelf();

    }

    private static Paint createPaint(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        return paint;
    }

}