package com.rebbity.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.rebbity.cn.RebbityApp;
import com.rebbity.cn.R;

/**
 * Created by Tyler on 15/1/2.
 */
public class RainbowBar extends View {
    private static final String Tag = "Rebbity";

    public static final int HEIGHT = RebbityApp.getG_context().getResources().getDimensionPixelSize(R.dimen.rainbow_bar_height);
    private Rainbow mRainbowBarAnimate = null;

    public RainbowBar(Context context) {
        super(context);
        init();
    }

    public RainbowBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RainbowBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] arr = super.onCreateDrawableState(extraSpace + 2);
        return arr;
    }

    private void init(){
        this.mRainbowBarAnimate = new Rainbow(this);
    }
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mRainbowBarAnimate != null) {
            this.mRainbowBarAnimate.setState(getDrawableState());
        }
        invalidate();
    }

    public Rainbow getRainbow(){
        return this.mRainbowBarAnimate;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return HEIGHT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(Tag, "rainbow bar is ("+ this.mRainbowBarAnimate == null ? "null" : "not null" +")");
        if (this.mRainbowBarAnimate != null) {
            this.mRainbowBarAnimate.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mRainbowBarAnimate != null) {
            this.mRainbowBarAnimate.setBounds(0, 0, w, h);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        if (this.mRainbowBarAnimate != null && who == this.mRainbowBarAnimate){
            return true;
        }

        return super.verifyDrawable(who);
    }
}
