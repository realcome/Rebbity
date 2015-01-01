package com.rebbity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class VLinearLayout extends LinearLayout implements IResizeListener{
	private IOnResizeListener mResizeListener;
	
	public VLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public VLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOnResizeListener(IOnResizeListener listener) {
		// TODO Auto-generated method stub
		this.mResizeListener = listener;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		if(this.mResizeListener != null){
			this.mResizeListener.onResize(this, w, h, oldw, oldh);
		}
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		// TODO Auto-generated method stub
		int[] superState = super.onCreateDrawableState(extraSpace + 2);
		//mergeDrawableStates();
		// TYLER_TODO
		return superState;
	}

}
