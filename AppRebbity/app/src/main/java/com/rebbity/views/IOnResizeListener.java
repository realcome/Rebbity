package com.rebbity.views;

import android.view.View;

public interface IOnResizeListener {
	public abstract void onResize(View view, int w, int h, int oldw, int oldh);
}
