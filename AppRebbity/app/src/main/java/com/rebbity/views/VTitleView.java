package com.rebbity.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

// TYLER_TODO
public class VTitleView extends View /* implement eventlistnener*/{
	private VTextView mText = null;

	public VTitleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public VTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public VTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public VTitleView setText(String text){
		if(text == null){
			// TYLER_TODO: draw empty string.
		}else{
			if(mText == null){
				this.mText = new VTextView();
			}
			// TYLER_TODO
			//this.mText.setStr(text);
			//this.mText.resource(getResources());
			setDrawable(this.mText);
		}
		
		return this;
	}
	
	public void setDrawable(Drawable titleDrawable){
		// TYLER_TODO
	}

}
