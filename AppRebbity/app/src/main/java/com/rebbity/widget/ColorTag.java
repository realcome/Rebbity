package com.rebbity.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ColorTag extends View{
	// stroke follow circle color or not.
	private Context mContext;
	private Paint mPaint;
	private float mRingWidth = 1.0f;// 2dpi
	private float mGapWidth = 2.0f; // 2dpi
	private boolean bRingColorFollow = false;
	private final String mTagDefaultColorStr = "#DDfcac15";
	private final String mRingDefaultColorStr = "#DDA2A2A2";
	private int mTagColor;
	private int mEndColor;
	private int mCurColor;
	

	private boolean mHasShadow = true;
	private boolean mHasRing = true; // TYLER_TODO: realize no ring state.

	public int getmEndColor() {
		return mEndColor;
	}

	public void setmEndColor(int mEndColor) {
		this.mEndColor = mEndColor;
	}

	public float getmRingWidth() {
		return mRingWidth;
	}

	public void setmRingWidth(float mRingWidth) {
		this.mRingWidth = mRingWidth;
	}

	public float getmGapWidth() {
		return mGapWidth;
	}

	public void setmGapWidth(float mGapWidth) {
		this.mGapWidth = mGapWidth;
	}

	public int getmTagColor() {
		return mTagColor;
	}

	public void setmTagColor(int mTagColor) {
		this.mTagColor = mTagColor;
	}

	public int getmRingColor() {
		return mRingColor;
	}

	public void setmRingColor(int mRingColor) {
		this.mRingColor = mRingColor;
	}


	private int mRingColor;
	
	
	public boolean isbRingColorFollow() {
		return bRingColorFollow;
	}

	public void setbRingColorFollow(boolean bRingColorFollow) {
		this.bRingColorFollow = bRingColorFollow;
	}

	
	
	public ColorTag(Context context) {
		super(context);
		this.mTagColor = Color.parseColor(mTagDefaultColorStr);
		this.mRingColor = Color.parseColor(mRingDefaultColorStr);
		this.mEndColor = this.mTagColor;
		this.mCurColor = this.mTagColor;
		this.mContext = context;
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		
		// TODO Auto-generated constructor stub
	}
	

	public ColorTag(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mTagColor = Color.parseColor(mTagDefaultColorStr);
		this.mRingColor = Color.parseColor(mRingDefaultColorStr);
		this.mEndColor = this.mTagColor;
		this.mCurColor = this.mTagColor;
		this.mContext = context;
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		// TODO Auto-generated constructor stub
	}

	public ColorTag(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mTagColor = Color.parseColor(mTagDefaultColorStr);
		this.mRingColor = Color.parseColor(mRingDefaultColorStr);
		this.mEndColor = this.mTagColor;
		this.mCurColor = this.mTagColor;
		this.mContext = context;
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		// TODO Auto-generated constructor stub
	}

	public static int dipToPix(Context context, float dipVal){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipVal * scale + 0.5f);
	}
	
	public void updateTag(float progress){
		int startColorR = Color.red(mTagColor);
		int startColorG = Color.green(mTagColor);
		int startColorB = Color.blue(mTagColor);
		int startColorA = Color.alpha(mTagColor);

		int endColorR = Color.red(mEndColor);
		int endColorG = Color.green(mEndColor);
		int endColorB = Color.blue(mEndColor);
		int endColorA = Color.alpha(mEndColor);
		
		int red = (int) (startColorR * (1.0f - progress) + endColorR * progress);
		int green = (int) (startColorG * (1.0f - progress) + endColorG * progress); 
		int blue = (int) (startColorB * (1.0f - progress) + endColorB * progress); 
		int alpha = (int) (startColorA * (1.0f - progress) + endColorA * progress);  
	
		this.mCurColor = Color.argb(alpha, red, green, blue);
		
		if(1.0f <= progress){
			this.mTagColor = this.mEndColor;
		}
		this.invalidate();
	}
	
	private int getInnerR(){
		int maxR = Math.min(getWidth(), getHeight()) / 2;
		int inner = maxR - dipToPix(mContext, mRingWidth + mGapWidth);
		
		if(inner <= 0){
			inner = 1;
		}
		
		return inner;
	}
	
	private int getRingR(){
		int maxR = Math.min(getWidth(), getHeight()) / 2;
		int inner = maxR - dipToPix(mContext, mRingWidth);
		
		return inner <=0 ? 1:inner;
	}
	
	private int getRingWidth(){
		return dipToPix(mContext, mRingWidth);
	}
	
	private int getRingColor(){
		if(true == bRingColorFollow){
			return mCurColor;
		}else{
			return mRingColor;
		}
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		int cx = getWidth() / 2;
		int cy = getHeight() / 2;
		
		// draw inner circle
		this.mPaint.setStyle(Style.FILL);
		this.mPaint.setColor(mCurColor);
		canvas.drawCircle(cx, cy, getInnerR(), mPaint);
		
		// draw ring.
		this.mPaint.setStyle(Style.STROKE);
		this.mPaint.setColor(getRingColor());
		this.mPaint.setStrokeWidth(getRingWidth());
		
		if(mHasShadow == true){
			int alpha = (int) (Color.alpha(mTagColor) * 1.25f);
			
			if(alpha >= 255){
				alpha = 255;
			}
			
			int color = Color.argb(alpha, Color.red(mTagColor), Color.green(mTagColor), Color.blue(mTagColor));
			this.mPaint.setShadowLayer(2, 0, 1, color);
		}
		
		canvas.drawCircle(cx, cy, getRingR(), mPaint);
 
		
		
	}
	
	
	

}
