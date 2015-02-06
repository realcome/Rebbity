package com.rebbity.widget;

import com.rebbity.cn.BaseActivity;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class ShadowView extends View{
	private Drawable mShadow = null;

	public ShadowView(Context context) {
		super(context);
	}

	public ShadowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	private int getRealWidth(int measureSpec){
		int ret;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		
		if(mode == MeasureSpec.AT_MOST){
			ret = size;
		}else{
			DisplayMetrics outMetrics = new DisplayMetrics();
			((WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
			ret = outMetrics.widthPixels;
		}
		
		ret = Math.min(ret, size);
		return ret;
	}
	
	private void setBounds(){
		if(this.mShadow == null){
			return ;
		}
		
		this.mShadow.setBounds(0, 0, getWidth(), getHeight());
	}
	
	private int getRealHeight(int measureSpec){
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int ret = 0;
		
		if(mode == MeasureSpec.AT_MOST){
			BaseActivity.showToast(getContext(), "sh: "+ret, Toast.LENGTH_LONG);
			ret = size;
		}else if(this.mShadow != null){
			int r = this.mShadow.getIntrinsicHeight();
			ret = Math.min(ret, size);

			BaseActivity.showToast(getContext(), "wh: "+ret, Toast.LENGTH_LONG);
		}

		return ret;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		if (this.mShadow != null)
		      this.mShadow.setState(getDrawableState());
		    invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(this.mShadow != null){
			this.mShadow.draw(canvas);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(changed == true){
			setBounds();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(getRealWidth(widthMeasureSpec), getRealHeight(heightMeasureSpec));
	}
	
	public int getIntrinsicHeight(){
		if(this.mShadow != null){
			return this.mShadow.getIntrinsicHeight();
		}
		
		return 0;
	}
	
	public void setDrawable(Drawable shadow){
		this.mShadow = shadow;
		setBounds();
		requestLayout();
		invalidate();
	}
}
