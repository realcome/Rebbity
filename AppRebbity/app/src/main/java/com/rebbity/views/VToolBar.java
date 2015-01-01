package com.rebbity.views;

import java.util.Locale;
import com.rebbity.widget.ShadowView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.rebbity.global.R;

public class VToolBar extends VLinearLayout{
	private Paint mPaint;
	private Drawable mDrawable;// TYLER_TODO: Rename
	private ShadowView mShadow;
	private VTitleView mTitle = null;
	
	private int mShadowId;
	private boolean mIsTop;
	private Locale mLocale = null;
	
	private ColorStateList aa;// TYLRE_TODO:rename
	
	public VToolBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.VToolBar);
		// TYLER_TODO
		//setHasRainbowBar(arr.getBoolean(R.attr.hasRainbowAnimBar, false));
		setTitle(arr.getString(R.attr.title));// TYLER_TODO: title pos.
		// TYLER_TODO
		//setTitleAlign(arr.getInteger(R.attr.titleAlign, 0));
		setIsTopToolbar(arr.getBoolean(R.attr.toolbarIsTop, false));
		this.mShadowId = arr.getResourceId(R.attr.shadow, 0); // 0 as null
	}
	
	public VToolBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean verifyDrawable(Drawable who) {
		// TODO Auto-generated method stub
		return super.verifyDrawable(who);
	}
	
	
	public void setTitle(int id){
		setTitle(getResources().getString(id));
	}
	
	public void setTitle(String title){
		// TYLER_TODO
		if(this.mLocale == null){
			this.mLocale = Locale.getDefault();
		}
		
		updateTitle(title, this.mLocale.getLanguage().equals(Locale.ENGLISH.getLanguage()));
	}
	
	public void setIsTopToolbar(boolean istop){
		this.mIsTop = istop;
	}
	
	public void updateTitle(String title, boolean isEn){
		if(title != null && true == isEn){
			// TYLER_TODO
			// get english str.
			//title = UtilResMgr.getEnglishStr(title);
		}
		
		if(true/*checkString(title) && title != null*/){
			getTitleView().setText(title);
		}
		
		if(this.mTitle == null){
			return;
		}
		
		if(title == null){
			this.mTitle.setText(null);
		}
	}
	
	public VTitleView getTitleView(){
		if(this.mTitle == null){
			// TYLER_TODO: may be to check layout.
			VTitleLayout layout = new VTitleLayout(getContext());
			//layout.setOrientation(0); // 0 as left to right and left align.
			// TYLER_TODO:params may be need to change.
			this.addView(layout, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,  1.0f));
			this.mTitle = new VTitleView(getContext());
			//this.mTitle.setId(id); // set id.
			layout.addView(this.mTitle, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		}
		
		return this.mTitle;
	}
	
}
